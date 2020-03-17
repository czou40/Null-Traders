package cores.NPCEncounters;

import cores.characters.Player;
import cores.objects.Item;

import java.util.Random;

public class Trader implements TradableNPC, RobbableNPC, IgnorableNPC {
    private Player player;
    private Item item;
    private int price;
    private int quantity;

    private static final int MAX_QUANTITY = 5;

    public Trader(Player player) {
        this.player = player;
        this.item = Item.values()[(int) (Math.random() * Item.values().length)];
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
        if(remain < quantity) {
            throw new Exception("You don't have enough space in your ship!");
        }
        player.getShip().load(item,  price, quantity);
    }

    @Override
    public boolean handleNegotiate() {
        final double PRICE_REDUCTION = 0.4;
        //if this method is used the option should also be greyed out in the UI

        double merchantSkillInfluence = player.calcInfluence(Player.SkillType.MER);
        Random random = new Random();
        double succeedNum = random.nextDouble();
        boolean succeed = succeedNum > 0.5 * (1-merchantSkillInfluence);
        if (succeed) {
            price = (int) (PRICE_REDUCTION * price);
            return true;
        } else {
            price = (int) ((1 + 1 / PRICE_REDUCTION) * price);
            return false;
        }
    }

    @Override
    public boolean handleRob() {
        boolean alwaysWin = false;
        boolean alwaysLose = false;
        final int MAX_STRENGTH = 50;

        double fightSkillInfluence = player.calcInfluence(Player.SkillType.FIG);
        Random random = new Random();
        double winOrLoseNum = random.nextDouble();
        boolean win = winOrLoseNum > 0.5 * (1 - fightSkillInfluence);
        if (alwaysWin) {
            win = true;
        } else if (alwaysLose) {
            win = false;
        }
        if(win) {
            player.getShip().load(item, 0, (int) (Math.random() * quantity) + 1);
            return true;
        } else {
            player.getShip().damage(
                    (int) Math.round(Math.random() * MAX_STRENGTH)
            );
            return false;
        }
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

    //<<<<<<< HEAD
    @Override
    public void handleIgnore() {
//=======
//    public void test() {
//
//    }
//
//    private void testForfeit() {
//
//    }
//
//    private void testFight() {
//
//    }
//
//    private void testFlee() {
//>>>>>>> 5ed09473fc430f44f580041a06d2eb71703c3d39

    }
}
