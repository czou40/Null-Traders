package cores.NPCEncounters;

import cores.characters.Player;

public interface Fightable {
    boolean handleFight(Player player);   //return if fight was successful
    boolean handleFlee(Player player);   //return if flee was successful
    boolean handleForfeit(Player player);
}
