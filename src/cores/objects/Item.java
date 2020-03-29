package cores.objects;

import java.util.ArrayList;

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
    COMPASS("Compass", 8, 2000, false),
    NULL("The Null Pointer", 11, 12000, false, false);

    private String name;
    private int techLevel;
    private int basePrice;
    private boolean illegal;
    private boolean onMarket;

    Item(String name, int techLevel, int basePrice, boolean illegal, boolean onMarket) {
        this.name = name;
        this.techLevel = techLevel;
        this.basePrice = basePrice;
        this.illegal = illegal;
        this.onMarket = onMarket;
    }

    Item(String name, int techLevel, int basePrice, boolean illegal) {
        this(name, techLevel, basePrice, illegal, true);
    }

    public static Item getRandomItemOnMarket() {
        ArrayList<Item> onMarketItems = new ArrayList<>(Item.values().length);
        for (Item i : Item.values()) {
            if (i.onMarket) {
                onMarketItems.add(i);
            }
        }
        return onMarketItems.get((int) (Math.random() * onMarketItems.size()));
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
        return illegal;
    }

    public boolean isOnMarket() {
        return onMarket;
    }

}
