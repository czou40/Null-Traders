package cores.NPCEncounters;

import cores.Game;
import cores.characters.Player;
import cores.objects.InventoryEntry;
import cores.objects.Item;
import cores.places.Region;
import javafx.stage.Stage;
import screens.EncounterScreen;
import screens.TraderScreen;

import java.util.Random;

public class Trader implements NPC, ITrade, Robbable {
    private Player player;
    private Item item;
    private InventoryEntry entry;
    private Region dest;

    private static final int MAX_QUANTITY = 5;

    public Trader(Player player, Region dest) {
        this.player = player;
        this.item = Item.values()[(int) (Math.random() * Item.values().length)];
        this.entry = new InventoryEntry();
        entry.add(item.getBasePrice(), (int) Math.round(Math.random() * MAX_QUANTITY));
        this.dest = dest;
    }

    @Override
    public EncounterScreen getEncounterScreen(Game game, Stage primaryStage) {
        return new TraderScreen(primaryStage, game, this);
    }

    @Override
    public boolean handleBuy() {
        if (player.getCredits() >= entry.getTotalCost()) {
            InventoryEntry playerEntry = player.getShip().getItemInventory().get(item);
            if (playerEntry == null) {
                playerEntry = new InventoryEntry();
            }
            playerEntry.add((int) entry.getAverageBuyingPrice(), entry.getQuantity());
            player.getShip().getItemInventory().put(item, playerEntry);

            player.travelToRegion(dest, true);
            return true;
        } else {
            return false;
        }
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
            entry.setTotalCost((int) (PRICE_REDUCTION * entry.getTotalCost()));
            return true;
        } else {
            entry.setTotalCost((int) ((1 + 1 / PRICE_REDUCTION) * entry.getTotalCost()));
            return false;
        }
    }

    @Override
    public boolean handleRob() {
        final int MAX_STRENGTH = 50;

        double fightSkillInfluence = player.calcInfluence(Player.SkillType.FIG);
        Random random = new Random();
        double winOrLoseNum = random.nextDouble();
        boolean win = winOrLoseNum > 0.5 * (1-fightSkillInfluence);

        if(win){
            InventoryEntry playerEntry = player.getShip().getItemInventory().get(item);
            if (playerEntry == null) {
                playerEntry = new InventoryEntry();
            }
            playerEntry.add(0, (int) Math.round(Math.random() * entry.getQuantity()));
            player.getShip().getItemInventory().put(item, playerEntry);

            player.travelToRegion(dest, true);
            return true;
        } else {
            player.getShip().damage(
                    (int) Math.round(Math.random() * MAX_STRENGTH)
            );

            player.travelToRegion(dest, true);
            return false;
        }
    }

    public void test() {

    }

    private void testForfeit() {

    }

    private void testFight() {

    }

    private void testFlee() {

    }
}
