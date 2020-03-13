package cores.NPCEncounters;

import cores.NPCEncounters.NPC;
import cores.characters.Player;
import cores.objects.Item;

public class Police implements NPC, Fightable {
    private Player player;
    private Item confiscatedItem;

    public Police(Player player, Item confiscatedItem) {
        this.player = player;
        this.confiscatedItem = confiscatedItem;
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
