package cores.NPCEncounters;

import cores.Game;
import cores.NPCEncounters.NPC;
import cores.characters.Player;
import cores.objects.Item;
import javafx.stage.Stage;
import screens.EncounterScreen;
import screens.PoliceScreen;
import screens.Screen;

public class Police implements NPC, Fightable {
    private Player player;
    private Item confiscatedItem;

    public Police(Player player, Item confiscatedItem) {
        this.player = player;
        this.confiscatedItem = confiscatedItem;
    }

    @Override
    public EncounterScreen getEncounterScreen(Game game, Stage primaryStage) {
        return new PoliceScreen(primaryStage, game, this);
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
    public void handleForfeit(Player player) {
        player.getShip().getItemInventory().remove(confiscatedItem);
    }
}
