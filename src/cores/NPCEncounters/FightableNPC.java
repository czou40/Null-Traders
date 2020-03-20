package cores.NPCEncounters;

public interface FightableNPC extends NPC{
    boolean handleFight();   //return if fight was successful
    boolean handleFlee();   //return if flee was successful
    void handleForfeit();
}
