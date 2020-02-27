import javafx.scene.Node;

import java.util.Vector;

public class MarketScreenController {
    private Player player;
    private Marketplace market;

    private MarketScreen ui;

    private static final boolean DEBUG = true;

    //UI elements so can display messages and stuff

    public MarketScreenController(Player player, Marketplace market, MarketScreen ui) {
        this.player = player;
        this.market = market;
        this.ui = ui;
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

        //check if the market has the item
        if (marketEntry == null) {
            throw new IllegalArgumentException("Attempted to buy from a market that does not have the item specified");
        }

        //Need to check if player has enough credits or cargo capacity is full
        if (price > player.getCredits()) {
            //Not enough credits case
        } else if (ship.getTotalItems() >= ship.getCargoCapacity()) {
            //Ship is full case
        } else {
            //update ship and market inventories accordingly; decrement player credits

            //update ship inventory
            if (playerEntry == null) {
                playerEntry = new InventoryEntry();
            }
            playerEntry.add(price);
            ship.getItemInventory().put(item, playerEntry);

            //update market inventory
            marketEntry.setQuantity(marketEntry.getQuantity() - 1); //decrement quantity
            if (marketEntry.getQuantity() <= 0) {
                market.getStock().remove(item); //remove the item from the stock if

                ui.removeItem(item);
            }

            //decrement player credits/update item count
            player.setCredits(player.getCredits() - price);
            ship.setTotalItems(ship.getTotalItems() + 1);

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
     * @param item  item to sell
     * @param market market to sell to
     */
    public void sell(Item item, Marketplace market) {
        Ship ship = player.getShip();
        InventoryEntry playerEntry = ship.getItemInventory().get(item);
        StockEntry marketEntry = market.getStock().get(item);
        int price = market.getSellingPrice(item);

        //check if the player has the item
        if (playerEntry == null) {
            throw new IllegalArgumentException("Attempted to sell an item that the player does not have");
        }

        //update ship inventory
        playerEntry.remove();
        if (playerEntry.getQuantity() <= 0) {
            ship.getItemInventory().remove(item);
        }

        //update market inventory
        if (marketEntry == null) {
            marketEntry = new StockEntry(0, market.getBuyingPrice(item), market.getSellingPrice(item));
        }
        marketEntry.setQuantity(marketEntry.getQuantity() + 1);
        market.getStock().put(item, marketEntry);


        //increment player credits
        player.setCredits(player.getCredits() + price);
        ship.setTotalItems(ship.getTotalItems() - 1);
    }
}
