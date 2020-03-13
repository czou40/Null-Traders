package cores.objects;

/**
 *
 */
public enum Item {
    WATER("Water", 1, 5, false),
    FURS("Furs", 2, 8, false),
    FOOD("Food", 1, 8, false),
    ORE("Ore", 4, 20, false),
    GAMES("Games", 4, 25, true),
    FIREARMS("Firearms", 6, 35, true),
    MEDICINE("Opioids", 6, 35, false),
    MACHINES("Machines", 7, 40, false),
    NARCOTICS("Narcotics", 7, 50, true),
    ROBOTS("Robots", 9, 100, true),
    NUKES("Nukes", 10, 200, true);

    private String name;
    private int techLevel;
    private int basePrice;
    private boolean illegal;

    Item(String name, int techLevel, int basePrice, boolean illegal) {
        this.name = name;
        this.techLevel = techLevel;
        this.basePrice = basePrice;
        this.illegal = illegal;
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

    public boolean isIllegal() { return illegal; }
}
