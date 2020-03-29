package cores.objects;

/**
 *
 */
public enum Item {
    WATER("Water", 1, 5, false),
    FURS("Furs", 2, 10, false),
    FOOD("Food", 1, 25, false),
    ORE("Ore", 4, 50, false),
    GAMES("Games", 4, 100, true),
    FIREARMS("Firearms", 6, 500, true),
    MEDICINE("Opioids", 6, 700, false),
    MACHINES("Machines", 7, 800, false),
    NARCOTICS("Narcotics", 7, 1000, true),
    ROBOTS("Robots", 9, 4000, true),
    NUKES("Nukes", 9, 10000, true),
    NULL("Null Pointer Exception", 10, 15000, false);


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

    public boolean isIllegal() {
        return illegal; }
}
