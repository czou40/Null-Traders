package cores.NPCEncounters;


import cores.objects.Item;

public interface TradableNPC extends NPC {
    void handleBuy() throws Exception;
    boolean handleNegotiate();
    Item getItem();
    int getPrice();
    int getQuantity();
}
