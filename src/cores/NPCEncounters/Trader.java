package cores.NPCEncounters;

import cores.Game;
import cores.characters.Player;
import cores.objects.InventoryEntry;
import cores.objects.Item;
import cores.objects.StockEntry;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.stage.Stage;
import screens.BanditScreen;
import screens.EncounterScreen;
import screens.Screen;
import screens.TraderScreen;

import java.util.Map;

public class Trader implements NPC, ITrade, Robbable {
    private Player player;
    private Item item;
    private InventoryEntry entry;

    private static final int MAX_QUANTITY = 5;

    public Trader(Player player) {
        this.player = player;
        this.item = Item.values()[(int) (Math.random() * Item.values().length)];
        this.entry = new InventoryEntry();
        entry.add(item.getBasePrice(), (int) Math.round(Math.random() * MAX_QUANTITY));
    }

    @Override
    public EncounterScreen getEncounterScreen(Game game, Stage primaryStage) {
        return new TraderScreen(primaryStage, game, this);
    }

    @Override
    public boolean handleBuy(Player player, Item item, int quantity) {
        return false;
    }

    @Override
    public boolean handleNegotiate(Player player) {

        return false;
    }

    @Override
    public boolean handleRob(Player player) {
        return false;
    }
}
