package cores.objects;

import java.util.ArrayList;

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
    OPIOIDS("Opioids", 6, 35, false),
    MACHINES("Machines", 7, 40, false),
    NARCOTICS("Narcotics", 7, 50, true),
    ROBOTS("Robots", 9, 100, true),
    NUKES("Nukes", 10, 200, true),
    COMPASS("Compass", 8, 2000, false),
    NULL("NullPointerException", 11, 12000, false, false);

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
