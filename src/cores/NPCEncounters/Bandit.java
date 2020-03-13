package cores.NPCEncounters;

import cores.characters.Player;

public class Bandit implements NPC, Fightable {
    private Player player;

    public Bandit(Player player) {
        this.player = player;
    }

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
        return false;
    }
}
