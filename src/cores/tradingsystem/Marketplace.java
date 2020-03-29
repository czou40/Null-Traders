package cores.tradingsystem;

import cores.characters.Player;
import cores.objects.Item;
import cores.objects.StockEntry;
import cores.objects.Upgrade;
import javafx.beans.property.SimpleBooleanProperty;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Marketplace {
    private String name;
    private int techLevel;
    private Map<Item, StockEntry> stock;
    private Player player;

    //constants used in price generation algorithm
    private static final int MAXITEMS = 100;
    private static final double MINQUANTITYFACTOR = 0.1;
    private static final double INCREMENTALQUANTITYFACTOR = 0.15;

    //percentage of which the price can vary
    private static final double BUYVARIANCE = 0.1;
    //The maximum amount that technology can influence buying price
    private static final double MAXTECHINFLUENCE = 0.3;
    //The percentage increase of each tech point above
    private static final double INCREMENTALTECHINFLUENCE = 0.1;

    private static final double SELLVARIANCE = 0.2;
    private static final double AVGSELLPERCENT = 0.8;

    private static final double MAX_MERCHANT_INFLUENCE = 0.3;

    private static final boolean DEBUG = false;

    //upgrade item info
    private Upgrade upgrade;
    private SimpleBooleanProperty boughtUpgrade;

    private boolean sellsSpecialItems;

    public Marketplace(String name, int techLevel, Player player, boolean sellsSpecialItems) {
        this.name = name;
        this.techLevel = techLevel;
        this.sellsSpecialItems = sellsSpecialItems;
        this.stock = generateRandomStock();
        this.player = player;
        if (DEBUG) {
            printStock();
        }
        this.upgrade = Upgrade.getRandomUpgrade(player, techLevel);
        this.boughtUpgrade = new SimpleBooleanProperty(false);
    }

    private Map<Item, StockEntry> generateRandomStock() {
        //NOTE: For each attribute of the stock, keep everything in doubles
        // until the final calculation

        Random rand = new Random();

        Item[] items = Item.values();
        Map<Item, StockEntry> stockMap = new HashMap<>();

        for (Item item : items) {
            if (techLevel >= item.getTechLevel() && (item.isOnMarket() || sellsSpecialItems)) {
                int techDifference = techLevel - item.getTechLevel();
                /*
                   Quantity Algorithm: item quantity is determined by a random amount
                   that is then multiplied by a technology factor. Higher tech = more of the item
                   Specifically, the max quantity starts at 10 for a region meeting the minimum
                   tech level, then increases by 20 at each additional point above the minimum until
                   reaching a hard cap at 100.
                 */
                double quantityFactor = Math.min(
                        1, MINQUANTITYFACTOR + INCREMENTALQUANTITYFACTOR * techDifference);
                int itemQuantity = (int) (rand.nextInt(MAXITEMS) * quantityFactor);
                if (!item.isOnMarket()) { //If this is a special item, there should only be one such item.
                    itemQuantity = 1;
                }
                if (itemQuantity > 0) {
                    /*
                       Buy Price Algorithm:
                       Base Price * tech influence factor + some percentage variance of the
                       base price
                       Higher tech = cheaper item because the region can produce it more efficiently
                     */
                    double buyVariance = randMinusOneToOne() * BUYVARIANCE;
                    double techInfluence = Math.min(
                            MAXTECHINFLUENCE, INCREMENTALTECHINFLUENCE * techDifference);
                    int buyingPrice =
                            Math.max(1,
                            (int) (item.getBasePrice() * (1 - techInfluence) * (1 + buyVariance)));
                    /*
                       Sell Price Algorithm: The selling price is a percantage of the buying price
                       with some linear variance below or above the average selling price
                     */
                    double sellFactor = AVGSELLPERCENT + randMinusOneToOne() * SELLVARIANCE;
                    int sellingPrice = Math.max(1, ((int) (buyingPrice * sellFactor)));
                    stockMap.put(item, new StockEntry(itemQuantity, buyingPrice, sellingPrice));
                }
            }
        }

        return stockMap;
    }

    private double randMinusOneToOne() {
        return 2 * (Math.random() - 0.5);
    }

    /*
     * gets buying price from stock factoring in merchant influence
     *
     */
    public int getBuyingPrice(Item item) {
        StockEntry marketEntry = this.getStock().get(item);
        if (DEBUG) {
            System.out.println("Player merchant influence: "
                    + player.calcInfluence(Player.SkillType.MER));
        }
        return (int) (
                (1 - player.calcInfluence(Player.SkillType.MER) * MAX_MERCHANT_INFLUENCE)
                        * marketEntry.getBuyingPrice());
    }

    /*
     * gets selling price from stock factoring in merchant influence
     */
    public int getSellingPrice(Item item) {
        StockEntry marketEntry = this.getStock().get(item);
        if (DEBUG) {
            System.out.println("Player merchant influence: "
                    + player.calcInfluence(Player.SkillType.MER));
        }
        return (int) (
                (1 + player.calcInfluence(Player.SkillType.MER) * MAX_MERCHANT_INFLUENCE)
                        * marketEntry.getSellingPrice());
    }

    public Map<Item, StockEntry> getStock() {
        return stock;
    }

    public void printStock() {
        //USE FOR TESTING ONLY
        System.out.println("Item Stock For " + name + "\n");
        for (Item item : stock.keySet()) {
            StockEntry entry = stock.get(item);

            System.out.println(item + ": ");
            System.out.println("Quantity: " + entry.getQuantity());
            System.out.println("Buying Price: " + entry.getBuyingPrice());
            System.out.println("Buying Price Merchant Influence: " + getBuyingPrice(item));
            System.out.println("Selling Price: " + entry.getSellingPrice());
            System.out.println("Selling Price Merchant Influence: " + getSellingPrice(item));
            System.out.println();
        }
    }

    public String getName() {
        return name;
    }

    public Upgrade getUpgrade() {
        return upgrade;
    }

    public boolean hasBoughtUpgrade() {
        return boughtUpgrade.get();
    }

    public SimpleBooleanProperty boughtUpgradeProperty() {
        return boughtUpgrade;
    }

    public void setBoughtUpgrade(boolean boughtUpgrade) {
        this.boughtUpgrade.set(boughtUpgrade);
    }
}
