package cores.NPCEncounters;

import cores.NPCEncounters.NPC;
import cores.characters.Player;
import cores.objects.Item;

public interface ITrade {
    boolean handleBuy(Player player);
    boolean handleNegotiate(Player player);
}
