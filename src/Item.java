/**
 * idk if Item should be buyable/sellable or StockEntry should be.
 * Right now, with this implementation, Item is just a name for lookup
 * in the marketplace map
 */
public class Item implements Buyable, Sellable {
    private String name;

    public void buy(Player player, Marketplace market) {

    }

    public void sell(Player player, Marketplace market) {

    }
}
