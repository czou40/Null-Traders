package cores.NPCEncounters;

import cores.NPCEncounters.NPC;
import cores.characters.Player;
import cores.objects.Item;

public interface ITrade {
    void handleBuy(Player player, Item item, int quantity);
    void handleNegotiate(Player player);
}
