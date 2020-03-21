package cores.tradingsystem;

import cores.characters.Player;
import cores.objects.InventoryEntry;
import cores.objects.Item;
import cores.objects.StockEntry;
import cores.objects.Upgrade;
import cores.vehicles.Ship;

public class TransactionSystem {
    private Player player;
    private Marketplace market;


    private static final boolean DEBUG = false;

    //UI elements so can display messages and stuff

    public TransactionSystem(Player player, Marketplace market) {
        this.player = player;
        this.market = market;
    }

    /**
     * Removes an item from the market stock and places it inside the player's inventory
     * Updates the player's credits as a result of the exchange
     *
     * @param item   item to buy
     * @param market market to buy from
     * @param number number of items to buy
     * @throws Exception if input is invalid
     */
    public void buy(Item item, Marketplace market, int number) throws Exception {
        Ship ship = player.getShip();
        InventoryEntry playerEntry = ship.getItemInventory().get(item);
        StockEntry marketEntry = market.getStock().get(item);
        int price = market.getBuyingPrice(item);

        //check if the market has the item
        if (marketEntry == null) {
            throw new IllegalArgumentException(
                    "Attempted to buy from a market that does not have the item specified");
        }
        //check if the player inputs the wrong number
        if (number == 0) {
            throw new IllegalArgumentException("Please specify a number for your purchase!");
        }
        //Need to check if player has enough credits or cargo capacity is full
        if (price * number > player.getCredits()) {
            //Not enough credits case
            throw new IllegalAccessException("You don't have enough money!");
        } else if (ship.getTotalItems() + number > ship.getCargoCapacity()) {
            //cores.Ship is full case
            throw new IllegalAccessException("You ship does not have enough space!");
        } else if (marketEntry.getQuantity() < number) {
            throw new IllegalAccessException("There are not that many " + item.getName()
                    + " in the market!");
        } else {
            //update ship and market inventories accordingly; decrement player credits

            //update ship inventory
//            if (playerEntry == null) {
//                playerEntry = new InventoryEntry();
//            }
//            playerEntry.add(price, number);
//            ship.getItemInventory().put(item, playerEntry);

            //update market inventory
            marketEntry.setQuantity(marketEntry.getQuantity() - number); //decrement quantity

            //decrement player credits/update item count
            player.spend(price * number);
//            ship.setTotalItems(ship.getTotalItems() + number);
            ship.load(item, price, number);

            if (DEBUG) {
                market.printStock();
                ship.printInventory();
            }
        }
    }

    /**
     * Removes an item from the player's inventory and places it in the market stock
     * Updates the player's credits as a result
     *
     * @param item   item to sell
     * @param market market to sell to
     * @param number number of items to sell
     */
    public void sell(Item item, Marketplace market, int number) {
        Ship ship = player.getShip();
        InventoryEntry playerEntry = ship.getItemInventory().get(item);
        StockEntry marketEntry = market.getStock().get(item);
        int price = market.getSellingPrice(item);

        //check if the player has the item
        if (playerEntry == null) {
            throw new IllegalArgumentException(
                    "Attempted to sell an item that the player does not have");
        }
        //check if the player inputs the wrong number
        if (number == 0) {
            throw new IllegalArgumentException("Please specify a number for your sale!");
        }
        if (playerEntry.getQuantity() < number) {
            throw new IllegalArgumentException("You don't have that many "
                    + item.getName() + " for sale!");
        }
//        //update ship inventory
//        playerEntry.remove(number);
//        if (playerEntry.getQuantity() <= 0) {
//            ship.getItemInventory().remove(item);
//        }

        //update market inventory
        if (marketEntry == null) {
            marketEntry = new StockEntry(0,
                    market.getBuyingPrice(item), market.getSellingPrice(item));
        }
        marketEntry.setQuantity(marketEntry.getQuantity() + number);
        market.getStock().put(item, marketEntry);


        //increment player credits
        player.earn(price * number);
        ship.unload(item, number);
//        ship.setTotalItems(ship.getTotalItems() - number);
    }

    public void buyUpgrade(Upgrade upgrade) throws Exception {
        int price = upgrade.getPrice();
        if (price > player.getCredits()) {
            throw new IllegalAccessException("You don't have enough money!");
        }
        market.setBoughtUpgrade(true);
        player.spend(price);
        player.updateUpgrade(upgrade);
    }
}
