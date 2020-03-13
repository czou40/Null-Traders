package cores.NPCEncounters;

import cores.Game;
import cores.NPCEncounters.NPC;
import cores.characters.Player;
import cores.objects.Item;
import cores.places.Region;
import javafx.stage.Stage;
import screens.EncounterScreen;
import screens.PoliceScreen;
import screens.Screen;

public class Police implements NPC, Fightable {
    private Player player;
    private Item confiscatedItem;
    private Region dest;

    public Police(Player player, Item confiscatedItem, Region dest) {
        this.player = player;
        this.confiscatedItem = confiscatedItem;
        this.dest = dest;
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
