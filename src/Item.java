/**
 *
 */
public enum Item {
    WATER("Water", 1, 5),
    FURS("Furs", 2, 8),
    FOOD("Food", 1,8),
    ORE("Ore", 4,20),
    GAMES("Games", 4, 25),
    FIREARMS("Firearms", 6, 35),
    MEDICINE("Medicine", 6, 35),
    MACHINES("Machines", 7, 40),
    NARCOTICS("Narcotics", 7, 50),
    ROBOTS("Robots", 9, 100);


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
