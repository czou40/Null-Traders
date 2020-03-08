package cores.NPCEncounters;

import cores.characters.Player;
import cores.objects.Item;
import cores.objects.StockEntry;

import java.util.Map;

public class Trader implements NPC, ITrade, Robbable {
    public Map<Item, StockEntry> stock;

    @Override
    public void interact() {

    }

    @Override
    public void handleBuy(Player player, Item item, int quantity) {

    }

    @Override
    public void handleNegotiate(Player player) {

    }

    @Override
    public boolean handleRob(Player player) {
        return false;
    }
}
