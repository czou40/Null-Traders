package cores.characters;

import cores.NPCEncounters.EncounterController;
import cores.Game;
import cores.objects.Item;
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
    private EncounterController encounterController;


    private static final double DECAY_FACTOR = 0.05;  //rate at which influence decays

    /**
     * Constructs a new instance.
     *
     * @param game     The game
     * @param name     The name
     * @param pilot    The pilot
     * @param fighter  The fighter
     * @param merchant The merchant
     * @param engineer The engineer
     * @param credits  The credits
     */
    public Player(Game game, String name, Integer pilot, Integer fighter,
                  Integer merchant, Integer engineer, Integer credits) {
        this.game = game;
        this.name = new SimpleStringProperty(name);
        this.credits = new SimpleIntegerProperty(credits);
        this.currentRegion = new SimpleObjectProperty<>();
        this.ship = new SimpleObjectProperty<>(new Ship(game.getDifficulty()));
        upgrades = new HashMap<>();
        skills = new HashMap<>();
        rawSkills = new HashMap<>();
        for (SkillType x : SkillType.values()) {
            upgrades.put(x, new SimpleObjectProperty<>());
            skills.put(x, new SimpleIntegerProperty(0));
            rawSkills.put(x, 0);
        }
        encounterController = new EncounterController(this);
    }

    /**
     * Constructs a new instance.
     *
     * @param game The game
     * @param name The name
     */
    public Player(Game game, String name) {
        this(game, name, 0, 0, 0, 0, game.getDifficulty().getCredits());
    }

    /**
     * Constructs a new instance.
     *
     * @param game The game
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
    //<<<<<<< HEAD
    public void startTravelToRegion(Region dest) {
        this.encounterController.handleEncounter(dest);
    }

    public boolean isDead() {
        return ship.get().getHealth() <= 0;
    }

    public void resumeTravelAfterEncounter(Region dest)  {
        getShip().decrementFuel(getCurrentRegion(), dest, calcInfluence(SkillType.PIL));
        currentRegion.get().setIsCurrentRegion(false);
        setCurrentRegion(dest);
    }

    public boolean ableToTravelTo(Region dest) {
        return getShip().ableToTravelTo(getCurrentRegion(), dest, calcInfluence(SkillType.PIL));
    }

    public void refuelShip() throws Exception {
        int cost = ship.get().getRefuelCost();
        spend(cost);
        ship.get().refillFuel();
    }

    public void repairShip() throws Exception {
        int cost = ship.get().getRepairCost(calcInfluence(SkillType.ENG));
        spend(cost);
        ship.get().repair();
    }


    public int calcRepairShipCost() {
        return ship.get().getRepairCost(calcInfluence(SkillType.ENG));
    }


    /*
    Condenses the player's skill points in a type into a number between 0 and 1 using an exponential
    decay system.
     */
    public double calcInfluence(SkillType skill) {
        return (1 - Math.exp(-1 * DECAY_FACTOR * skills.get(skill).get()));
    }

    public void updateUpgrade(Upgrade upgrade) {
        SkillType type = upgrade.getUpgradeType();
        upgrades.get(type).set(upgrade);
        skills.get(type).set(upgrade.getUpgradeLvl() + rawSkills.get(type));
    }

    public boolean hasCompass() {
        return ship.get().getItemInventory().containsKey(Item.COMPASS);
    }

    /**
     * Gets the game.
     *
     * @return The game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * { function_description }
     *
     * @return The string property.
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Gets the name.
     *
     * @return The name.
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

    public void loseAllCredits() {
        credits.set(0);
    }

    public void loseCredits(int credit) {
        credits.set(Math.max(credits.get() - credit, 0));
    }

    public void earn(int credit) {
        credits.set(credits.get() + credit);
    }

    public void spend(int credit) throws Exception {
        if (credit > credits.get()) {
            throw new Exception("You do not have enough money!");
        }
        credits.set(credits.get() - credit);
    }

    public int sumOfPoints() {
        int sum = 0;
        for (SkillType x : SkillType.values()) {
            sum += skills.get(x).get();
        }
        return sum;
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