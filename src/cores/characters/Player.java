package cores.characters;

import cores.Game;
import cores.vehicles.Ship;
import cores.objects.Upgrade;
import cores.places.Region;
import javafx.beans.property.*;

import java.util.HashMap;

/**
 * This class describes a player.
 */
public class Player {
    public enum SkillType {
        PIL("Pilot"), FIG("Fighter"), MER("Merchant"), ENG("Engineer");
        private String name;

        SkillType(String name) {
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    private Game game;  //player is dependent on the game

    private StringProperty name;
    private HashMap<SkillType, SimpleObjectProperty<Upgrade>> upgrades;
    private HashMap<SkillType, IntegerProperty> skills;
    private HashMap<SkillType, Integer> rawSkills;
    private IntegerProperty credits;
    private SimpleObjectProperty<Region> currentRegion;
    private SimpleObjectProperty<Ship> ship;

    private static final double MAX_MERCHANT_INFLUENCE = 0.3; //can get a maximum of 30% off each item
    private static final double MAX_PILOT_INFLUENCE = 0.4;
    private static final double DECAY_FACTOR = 0.05;  //rate at which influence decays

    /**
     * Constructs a new instance.
     *
     * @param      game      The game
     * @param      name      The name
     * @param      pilot     The pilot
     * @param      fighter   The fighter
     * @param      merchant  The merchant
     * @param      engineer  The engineer
     * @param      credits   The credits
     */
    public Player(Game game, String name, Integer pilot, Integer fighter,
                  Integer merchant, Integer engineer, Integer credits) {
        this.game = game;
        this.name = new SimpleStringProperty(name);
        this.credits = new SimpleIntegerProperty(credits);
        this.currentRegion = new SimpleObjectProperty<>();
        this.ship = new SimpleObjectProperty<>(new Ship(game.getDifficulty()));
        upgrades = new HashMap<>();
        upgrades.put(SkillType.FIG, new SimpleObjectProperty<>());
        upgrades.put(SkillType.MER, new SimpleObjectProperty<>());
        upgrades.put(SkillType.ENG, new SimpleObjectProperty<>());
        upgrades.put(SkillType.PIL, new SimpleObjectProperty<>());

        skills = new HashMap<>();
        skills.put(SkillType.MER, new SimpleIntegerProperty(0));
        skills.put(SkillType.ENG, new SimpleIntegerProperty(0));
        skills.put(SkillType.FIG, new SimpleIntegerProperty(0));
        skills.put(SkillType.PIL, new SimpleIntegerProperty(0));

        rawSkills = new HashMap<>();
        rawSkills.put(SkillType.MER, 0);
        rawSkills.put(SkillType.ENG, 0);
        rawSkills.put(SkillType.FIG, 0);
        rawSkills.put(SkillType.PIL, 0);
    }

    /**
     * Constructs a new instance.
     *
     * @param      game  The game
     * @param      name  The name
     */
    public Player(Game game, String name) {
        this(game, name, 0, 0, 0, 0, game.getDifficulty().getCredits());
    }

    /**
     * Constructs a new instance.
     *
     * @param      game  The game
     */
    public Player(Game game) {
        this(game, "", 0, 0, 0, 0, game.getDifficulty().getCredits());
    }

    public void setCurrentRegion(Region currentRegion) {
        this.currentRegion.set(currentRegion);
        currentRegion.setFound(true);
        currentRegion.setIsCurrentRegion(true);
    }

    /*
    Returns whether the travel was successful
     */
    public boolean travelToRegion(Region dest) {
        System.out.println(getCurrentRegion().distanceTo(dest) / 10);
        int fuelNeeded = (int) (getCurrentRegion().distanceTo(dest) / 10 * calcPilotInfluence());
        if (getShip().getFuel() < fuelNeeded) {     //check if the player has enough fuel
            //not enough fuel
            return false;
        } else {
            currentRegion.get().setIsCurrentRegion(false);
            setCurrentRegion(dest);
            getShip().setFuel(getShip().getFuel() - fuelNeeded);
            System.out.println(fuelNeeded);

            return true;
        }
    }

    public double calcPilotInfluence() {
        return 1 - MAX_PILOT_INFLUENCE * (1 - Math.exp(-1 * DECAY_FACTOR
                * skills.get(SkillType.PIL).get()));
    }

    public double calcMerchantInfluence() {
        return MAX_MERCHANT_INFLUENCE * (1 - Math.exp(-1 * DECAY_FACTOR
                * skills.get(SkillType.MER).get()));
    }

    public void updateUpgrade(Upgrade upgrade) {
        SkillType type = upgrade.getUpgradeType();
        upgrades.get(type).set(upgrade);
        skills.get(type).set(upgrade.getUpgradeLvl() + rawSkills.get(type));
    }

    /**
     * Gets the game.
     *
     * @return     The game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * { function_description }
     *
     * @return     The string property.
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Gets the name.
     *
     * @return     The name.
     */
    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public IntegerProperty creditsProperty() {
        return credits;
    }

    public Integer getCredits() {
        return credits.getValue();
    }

    public void setCredits(Integer credits) {
        this.credits.setValue(credits);
    }

    public int sumOfPoints() {
        return skills.get(SkillType.MER).get()
                + skills.get(SkillType.FIG).get()
                + skills.get(SkillType.PIL).get()
                + skills.get(SkillType.ENG).get();
    }

    public Region getCurrentRegion() {
        return currentRegion.get();
    }

    public SimpleObjectProperty<Region> currentRegionProperty() {
        return currentRegion;
    }

    public Ship getShip() {
        return ship.get();
    }

    public SimpleObjectProperty<Ship> shipProperty() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship.set(ship);
    }

    public int getRawSkill(SkillType type) {
        return rawSkills.get(type);
    }

    public void setRawSkill(SkillType type, int point) {
        rawSkills.put(type, point);
        skills.get(type).set(point
                + (getUpgrade(type) != null ? getUpgrade(type).getUpgradeLvl() : 0));
        System.out.println(skills.get(type).get());
    }

    public IntegerProperty skillProperty(SkillType type) {
        return skills.get(type);
    }

    public Upgrade getUpgrade(SkillType type) {
        return upgrades.get(type).get();
    }

    public SimpleObjectProperty<Upgrade> getUpgradeProperty(SkillType type) {
        return upgrades.get(type);
    }
}
