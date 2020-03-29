package cores.NPCEncounters;

import cores.characters.Player;
import cores.objects.Item;
import javafx.util.Pair;

import java.util.Random;

public class Trader implements TradableNPC, RobbableNPC, IgnorableNPC {
    private Player player;
    private Item item;
    private int price;
    private int quantity;

    private static final int MAX_QUANTITY = 5;
    private static final double PRICE_REDUCTION = 0.4;

    public Trader(Player player) {
        this.player = player;
        this.item = Item.getRandomItemOnMarket();
        this.price = item.getBasePrice();
        this.quantity = (int) (Math.random() * MAX_QUANTITY) + 1;
    }

    //    @Override
    //    public EncounterOptionScreen getEncounterScreen(Game game, Stage primaryStage) {
    //        return new TradeScreen(primaryStage, game, this);
    //    }

    @Override
    public void handleBuy() throws Exception {
        int cost = price * quantity;
        if (player.getCredits() < cost) {
            throw new Exception("You don't have enough money!");
        }
        int remain = player.getShip().getCargoCapacity() - player.getShip().getTotalItems();
        if (remain < quantity) {
            throw new Exception("You don't have enough space in your ship!");
        }

        player.loseCredits(cost);
        player.getShip().load(item,  price, quantity);
    }

    @Override
    public boolean handleNegotiate() {
        double merchantSkillInfluence = player.calcInfluence(Player.SkillType.MER);
        Random random = new Random();
        double succeedNum = random.nextDouble();
        boolean succeed = succeedNum > 0.8 * (1 - merchantSkillInfluence);
        if (succeed) {
            price = (int) (PRICE_REDUCTION * price);
            return true;
        } else {
            price = (int) ((1 + PRICE_REDUCTION) * price);
            return false;
        }
    }

    @Override
    public Pair<Boolean, String> handleRob() {
        boolean alwaysWin = false;
        boolean alwaysLose = false;
        final int maxStrength = 50;

        double fightSkillInfluence = player.calcInfluence(Player.SkillType.FIG);
        Random random = new Random();
        double winOrLoseNum = random.nextDouble();
        boolean win = winOrLoseNum > 0.8 * (1 - fightSkillInfluence);
        if (alwaysWin) {
            win = true;
        } else if (alwaysLose) {
            win = false;
        }
        if (win) {
            int quantityRobbed = (int) (Math.random() * quantity) + 1;
            player.getShip().load(item, 0, quantityRobbed);
            return new Pair<>(true, "You successfully robbed " + quantityRobbed + " "
            + item.getName() + "(s) from the Trader.");
        } else {
            int damage = (int) Math.round(Math.random() * maxStrength);
            player.getShip().damage(damage);
            return new Pair<>(false, "Unfortunately, the Trader fought you off and damaged "
            + "your ship by " + damage + " points.");
        }
    }

    @Override
    public String getDescription() {
        return "A trader hopes to sell some " + item.getName() + "(s) to you.";
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }
}
