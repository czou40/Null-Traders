package cores.NPCEncounters;

import cores.NPCEncounters.NPC;
import cores.characters.Player;

public class Police implements NPC, Fightable {

    @Override
    public void interact() {

    }

    @Override
    public boolean handleFight(Player player) {
        return false;
    }

    @Override
    public boolean handleFlee(Player player) {
        return false;
    }

    @Override
    public boolean handleForfeit(Player player) {
    }
}
