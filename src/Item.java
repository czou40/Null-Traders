/**
 * idk if Item should be buyable/sellable or StockEntry should be.
 * Right now, with this implementation, Item is just a name for lookup
 * in the marketplace map
 */
public enum Item {
    // Water, Furs, Food, Ore, Games, Firearms, Medicine,Machines, Narcotics, Robots
    WATER("Water", 1),
    FURS("Furs", 2),
    FOOD("Food", 1),
    ORE("Ore", 4),
    GAMES("Games", 4),
    FIREARMS("Firearms", 6),
    MEDICINE("Medicine", 6),
    MACHINES("Machines", 7),
    NARCOTICS("Narcotics", 7),
    ROBOTS("Robots", 9);


    private String name;
    private int techLevel;

    Item(String name, int techLevel){
        this.name = name;
        this.techLevel = techLevel;
    }

    public String getName() {return name; }

    public int getTechLevel() { return techLevel; }

//
//    public void buy(Player player, Marketplace market) {
//
//    }
//
//    public void sell(Player player, Marketplace market) {
//
//    }
}
