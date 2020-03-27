package cores.NPCEncounters;

import javafx.util.Pair;

public interface FightableNPC extends NPC{
    Pair<Boolean, String> handleFight();   //return if fight was successful
    Pair<Boolean, String> handleFlee();   //return if flee was successful
    String handleForfeit();
}
