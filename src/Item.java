/**
 *
 */
public enum Item {
    WATER("Water", 1, 5),
    FURS("Furs", 2, 8),
    FOOD("Food", 1, 8),
    ORE("Ore", 4, 20),
    GAMES("Games", 4, 25),
    FIREARMS("Firearms", 6, 35),
    MEDICINE("Medicine", 6, 35),
    MACHINES("Machines", 7, 40),
    NARCOTICS("Narcotics", 7, 50),
    ROBOTS("Robots", 9, 100);


    private String name;
    private int techLevel;
    private int basePrice;

    Item(String name, int techLevel, int basePrice) {
        this.name = name;
        this.techLevel = techLevel;
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public int getTechLevel() {
        return techLevel;
    }

    public int getBasePrice() {
        return basePrice;
    }
}
