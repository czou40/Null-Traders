package cores.NPCEncounters;

import cores.NPCEncounters.NPC;
import cores.characters.Player;
import cores.objects.InventoryEntry;
import cores.objects.Item;

public interface TradableNPC extends NPC{
    void handleBuy() throws Exception;
    boolean handleNegotiate();
    Item getItem();
    int getPrice();
    int getQuantity();
}
