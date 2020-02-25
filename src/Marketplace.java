import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Marketplace {
    private String name;
    private int techLevel;
    private Map<Item, StockEntry> stock;

    //constants used in price generation algorithm
    private static final int MAXITEMS = 100;
    private static final double MINQUANTITYFACTOR = 0.1;
    private static final double INCREMENTALQUANTITYFACTOR = 0.15;

    private static final double BUYVARIANCE = 5;
    private static final double MAXTECHINFLUENCE = 0.5;
    private static final double INCREMENTALTECHINFLUENCE = 0.1;

    private static final double SELLVARIANCE = 0.2;
    private static final double AVGSELLPERCENT = 0.6;

    private static final boolean DEBUG = true;



    public Marketplace(String name, int techLevel) {
        this.name = name;
        this.techLevel = techLevel;
        this.stock = generateRandomStock();

        if (DEBUG) {
            printStock();
        }
    }

    private Map<Item, StockEntry> generateRandomStock() {

        Random rand = new Random();

        Item[] items = Item.values();
        Map<Item, StockEntry> stockMap = new HashMap<>();

        for(Item item: items){
            if (techLevel >= item.getTechLevel()) {
                int techDifference = techLevel - item.getTechLevel();

                /*
                Quantity Algorithm: item quantity is determined by a random amount that is then multiplied
                buy a technology factor. Higher tech = more of the item
                 */
                double quantityFactor = Math.min(1, MINQUANTITYFACTOR + INCREMENTALQUANTITYFACTOR * techDifference);
                int itemQuantity = (int) (rand.nextInt(MAXITEMS) * quantityFactor);

                if (itemQuantity > 0) {
                    /*
                    Buy Price Algorithm:
                     */
                    int buyVariance = (int) (2 * (Math.random() - 0.5) * BUYVARIANCE);
                    double techInfluence = Math.max(MAXTECHINFLUENCE, INCREMENTALTECHINFLUENCE * techDifference);
                    int buyingPrice = ((int) (item.getBasePrice() * (1 - techInfluence) + buyVariance));
                    /*
                    Sell Price Algorithm: The selling price is a percantage of the buying price
                    with some linear variance below or above the average selling price
                     */
                    double sellFactor = AVGSELLPERCENT + 2 * (Math.random() - 0.5) * SELLVARIANCE;
                    int sellingPrice = ((int) (item.getBasePrice() * sellFactor));

                    stockMap.put(item, new StockEntry(itemQuantity, buyingPrice, sellingPrice));
                }
            }
        }

        return stockMap;
    }

    public Map<Item, StockEntry> getStock() {
        return stock;
    }

    private void printStock() {
        //USE FOR TESTING ONLY
        System.out.println("Item Stock For " + name + "\n");
        for (Item item : stock.keySet()) {
            StockEntry entry = stock.get(item);

            System.out.println(item + ": ");
            System.out.println("Quantity: " + entry.getQuantity());
            System.out.println("Buying Price: " + entry.getBuyingPrice());
            System.out.println("Selling Price: " + entry.getSellingPrice());
            System.out.println();
        }
    }
}
