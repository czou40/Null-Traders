public class MarketScreenController {
    Player player;
    Marketplace market;

    //UI elements so can display messages and stuff

    public MarketScreenController(Player player, Marketplace market) {
        this.player = player;
        this.market = market;
    }

    /**
     * Removes an item from the market stock and places it inside the player's inventory
     * Updates the player's credits as a result of the exchange
     * @param item item to buy
     * @param market market to buy from
     */
    public void buy(Item item, Marketplace market) {
        //Still need to handle a few cases:
        //For now, buying will be 1 at a time,
        //but once this works we can add buying multiple items if time permits

        Ship ship = player.getShip();
        InventoryEntry playerEntry = ship.getItemInventory().get(item);
        StockEntry marketEntry = market.getStock().get(item);
        int price = market.getBuyingPrice(item);

        //Need to check if player has enough credits or cargo capacity is full
        if (price > player.getCredits()) {
            //Not enough credits case
        } else if (ship.calcTotalItems() >= ship.getCargoCapacity()) {
            //Ship is full case
        } else {
            //update ship and market inventories accordingly; decrement player credits

            //update ship inventory
            if (playerEntry == null) {
                ship.getItemInventory().put(item, new InventoryEntry());
            }
            playerEntry.add(price);

            //update market inventory
            marketEntry.setQuantity(marketEntry.getQuantity() - 1); //decrement quantity
            if (marketEntry.getQuantity() <= 0) {
                market.getStock().remove(item); //remove the item from the stock if necessary
            }

            //decrement player credits
            player.setCredits(player.getCredits() - price);
        }
    }

    /**
     * Removes an item from the player's inventory and places it in the market stock
     * Updates the player's credits as a result
     *
     * @param item  item to sell
     * @param market market to sell to
     */
    public void sell(Item item, Marketplace market) {
        Ship ship = player.getShip();
        InventoryEntry playerEntry = ship.getItemInventory().get(item);
        StockEntry marketEntry = market.getStock().get(item);
        int price = market.getSellingPrice(item);

        //TODO: implement
    }
}
