import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Marketplace {
    private String name;
    /*
    I was thinking for marketplaces we could implement the item stock as a map from the item name
    to an item collection, since each marketplace can have multiple items and the prices are supposed
    to be unique to the marketplace.
    */
    private Map<Item, StockEntry> stock;


    public Marketplace(String name, int merchantSkill) {
        this.name = name;
        this.stock = generateRandomStock(merchantSkill);
    }

    private Map<Item, StockEntry> generateRandomStock(int merchantSkill) {
        //This is where we do the algorithm for generating the stock
        //Only for testing purposes
        Map<Item, StockEntry> stock = new HashMap<>();
        for (Item i : Item.values()) {
            stock.put(i, new StockEntry(10, 2939, 2245));
        }
        return stock;
    }

    public String getName() {
        return name;
    }

    public Map<Item, StockEntry> getStock() {
        return stock;
    }
}
