package cores.NPCEncounters;

import cores.characters.Player;

public interface Fightable {
    boolean handleFight();   //return if fight was successful
    boolean handleFlee();   //return if flee was successful
    void handleForfeit();
}
