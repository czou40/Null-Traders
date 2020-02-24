/**
 * idk if Item should be buyable/sellable or StockEntry should be.
 * Right now, with this implementation, Item is just a name for lookup
 * in the marketplace map
 */

public enum Item {
    WATER("Water", 1, 1),
    FURS("Furs", 2, 2),
    FOOD("Food", 1,2),
    ORE("Ore", 4,4),
    GAMES("Games", 4, 3),
    FIREARMS("Firearms", 6, 6),
    MEDICINE("Medicine", 6, 4),
    MACHINES("Machines", 7, 6),
    NARCOTICS("Narcotics", 7, 7),
    ROBOTS("Robots", 9, 10);


    private String name;
    private int techLevel;
    private int basePrice;

    Item(String name, int techLevel, int basePrice){
        this.name = name;
        this.techLevel = techLevel;
        this.basePrice = basePrice;
    }

    public String getName() {return name; }

    public int getTechLevel() { return techLevel; }

    public int getBasePrice() { return basePrice; }

//    public void buy(Player player, Marketplace market, Ship ship) {
//        if(ship.getItemInventory().size() < ship.getCargoCapacity()) {
//            ship.getItemInventory().put(this, market.getStock().get(this));
//
//            //I added getters to Marketplace and StockEntry, but there is probably some way I could use properties
//            player.setCredits(player.getCredits() - market.getStock().get(this).getBuyingPrice());
//            market.getStock().remove(this);
//        } else{
//            //set an error message on the marketplace screen saying something like "Not enough credits to purchase item"
//        }
//
//    }

//    public void sell(Player player, Marketplace market, Ship ship) {
//
//        //I added getters to Marketplace and StockEntry, but there is probably some way I could use properties
//        player.setCredits(player.getCredits() + market.getStock().get(this).getSellingPrice());
//        ship.getItemInventory().remove(this);
//
//    }
}
