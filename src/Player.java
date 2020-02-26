import javafx.beans.property.*;

/**
 * This class describes a player.
 */
public class Player {
    private Game game;  //player is dependent on the game

    private StringProperty name;
    private IntegerProperty pilot;
    private IntegerProperty fighter;
    private IntegerProperty merchant;
    private IntegerProperty engineer;
    private IntegerProperty credits;
    private SimpleObjectProperty<Region> currentRegion;
    private SimpleObjectProperty<Ship> ship;

    private static final double MAXMERCHANTINFLUENCE = 0.3; //can get a maximum of 30% off each item
    private static final double MERCHANTDECAYFACTOR = 0.05;  //rate at which influence decays
                                                            // (need to test to find reasonable value)


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
        this.pilot = new SimpleIntegerProperty(pilot);
        this.fighter = new SimpleIntegerProperty(fighter);
        this.merchant = new SimpleIntegerProperty(merchant);
        this.engineer = new SimpleIntegerProperty(engineer);
        this.credits = new SimpleIntegerProperty(credits);
        this.currentRegion = new SimpleObjectProperty<>();
        this.ship = new SimpleObjectProperty<>(new Ship(game.getDifficulty()));
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

    /**
     * Gets the game.
     *
     * @return     The game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets the game.
     *
     * @param      game  The game
     */
    public void setGame(Game game) {
        this.game = game;
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
        this.name = new SimpleStringProperty(name);
    }

    public IntegerProperty pilotProperty() {
        return pilot;
    }

    public Integer getPilot() {
        return pilot.getValue();
    }

    public void setPilot(Integer pilot) {
        this.pilot = new SimpleIntegerProperty(pilot);
    }

    public IntegerProperty fighterProperty() {
        return fighter;
    }

    public Integer getFighter() {
        return fighter.getValue();
    }

    public void setFighter(Integer fighter) {
        this.fighter = new SimpleIntegerProperty(fighter);
    }

    public IntegerProperty merchantProperty() {
        return merchant;
    }

    public Integer getMerchant() {
        return merchant.getValue();
    }

    public void setMerchant(Integer merchant) {
        this.merchant = new SimpleIntegerProperty(merchant);
    }

    public IntegerProperty engineerProperty() {
        return engineer;
    }

    public Integer getEngineer() {
        return engineer.getValue();
    }

    public void setEngineer(Integer engineer) {
        this.engineer = new SimpleIntegerProperty(engineer);
    }

    public IntegerProperty creditsProperty() {
        return credits;
    }

    public Integer getCredits() {
        return credits.getValue();
    }

    public void setCredits(Integer credits) {
        this.credits = new SimpleIntegerProperty(credits);
    }

    public Integer sumOfPoints() {
        return getPilot() + getFighter() + getMerchant() + getEngineer();
    }

    public Region getCurrentRegion() {
        return currentRegion.get();
    }

    public void setCurrentRegion(Region currentRegion) {
        this.currentRegion.set(currentRegion);
        currentRegion.setFound(true);
        currentRegion.setIsCurrentRegion(true);
    }

    public SimpleObjectProperty<Region> currentRegionProperty() {
        return currentRegion;
    }

    public Ship getShip() { return ship.get(); }

    public SimpleObjectProperty<Ship> shipProperty() { return ship; }

    public void setShip(Ship ship) { this.ship.set(ship); }

    public void travelToRegion(Region dest) {
        currentRegion.get().setIsCurrentRegion(false);
        setCurrentRegion(dest);
        /*
        More things will happen when the player travels.
        They will be coded in future implementations.
         */
        System.out.println(currentRegion.get().getName());
    }

    public double calcMerchantInfluence() {
        return MAXMERCHANTINFLUENCE * (1 - Math.exp(-1 * MERCHANTDECAYFACTOR * merchant.get()));
    }
}
