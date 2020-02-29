import java.nio.charset.Charset;
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
    private static final double AVGSELLPERCENT = 0.6;

    private static final boolean DEBUG = false;

    //upgrade item info
    private Player.SkillType upgradeType;
    private int upgradeLvl;
    private String upgradeName;
    private boolean hasBoughtUpgrade;

    public Marketplace(String name, int techLevel, Player player) {
        this.name = name;
        this.techLevel = techLevel;
        this.stock = generateRandomStock();
        this.player = player;
        if (DEBUG) {
            printStock();
        }

        //upgrade item generation
        Random upGen = new Random();
        int diff = player.getGame().getDifficulty().ordinal();
        this.upgradeType = Player.SkillType.values()[upGen.nextInt(Player.SkillType.values().length)];
        this.upgradeLvl = ((6 / (diff+1)) + (upGen.nextInt(techLevel+1)));
        hasBoughtUpgrade = false;

        String bet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < techLevel/2; ++i) {
            sb.append(bet.charAt(upGen.nextInt(bet.length())));
        }
        String nameGen = sb.toString();
        switch(upgradeType) {
            case PIL:
                upgradeName = nameGen.toUpperCase() +"corp Engine Upgrade v" + (upGen.nextInt(9)+1) + "." + (upGen.nextInt(9)+1);
                break;
            case FIG:
                upgradeName = nameGen.toUpperCase() + "corp Weapons Module v" + (upGen.nextInt(9)+1) + "." + (upGen.nextInt(9)+1);
                break;
            case MER:
                upgradeName = ((char)(upGen.nextInt(26) + 'A')) + "/" + ((char)(upGen.nextInt(26) + 'A'))
                        + " TradeAssist Mk. " + (upGen.nextInt(9)+1);
                break;
            case ENG:
                upgradeName = nameGen.toUpperCase() + "corp Multitool " + (upGen.nextInt(9)+1) + "-" + (upGen.nextInt(9)+1);
                break;
        }
    }

    private Map<Item, StockEntry> generateRandomStock() {
        //NOTE: For each attribute of the stock, keep everything in doubles
        // until the final calculation

        Random rand = new Random();

        Item[] items = Item.values();
        Map<Item, StockEntry> stockMap = new HashMap<>();

        for (Item item : items) {
            if (techLevel >= item.getTechLevel()) {
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
            System.out.println("Player merchant influence: " + player.calcMerchantInfluence());
        }
        return (int) ((1 - player.calcMerchantInfluence()) * marketEntry.getBuyingPrice());
    }

    /*
     * gets selling price from stock factoring in merchant influence
     */
    public int getSellingPrice(Item item) {
        StockEntry marketEntry = this.getStock().get(item);
        if (DEBUG) {
            System.out.println("Plyaer merchant influence: " + player.calcMerchantInfluence());
        }
        return (int) ((1 + player.calcMerchantInfluence()) * marketEntry.getSellingPrice());
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
}
