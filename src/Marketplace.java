import java.util.Map;
import java.util.Random;

public class Marketplace {
    private String name;
    /*
    I was thinking for marketplaces we could implement the item stock as a map from the item name
    to an item collection, since each marketplace can have multiple items and the prices are supposed
    to be unique to the marketplace.
    */
    private int techLevel;
    private Map<Item, StockEntry> stock;


    public Marketplace(String name, int techLevel) {

        this.name = name;
        this.techLevel = techLevel;
        this.stock = generateRandomStock(techLevel);
    }

    public Map<Item, StockEntry> getStock() {
        return stock;
    }

    private Map<Item, StockEntry> generateRandomStock(int techLevel) {
        //This is where we do the algorithm for generating the stock

        Random randomInt = new Random();

        Item[] items = Item.values();
        Map<Item, StockEntry> stockMap = null;

        for(Item i: items){
            StockEntry itemEntry = new StockEntry();
            int itemQuantity = randomInt.nextInt(16); //arbitrarily set the quantity limit at 15
            itemEntry.setQuantity(itemQuantity);

            //Note: Everything is just its base price for the time being, until we can implement the pricing algorithm
            itemEntry.setBuyingPrice(i.getBasePrice());
            itemEntry.setSellingPrice(i.getBasePrice() + 2); //Arbitrarily set selling price as buyingPrice + 2

            if(i.getTechLevel() >= techLevel){
                stockMap.put(i, itemEntry);
            }
        }

        return stockMap;
    }
}
