package cores.NPCEncounters;

import cores.Game;
import cores.characters.Player;
import cores.places.Region;
import javafx.stage.Stage;
import screens.EncounterScreen;
import screens.NothingHappenedScreen;

public class EncounterController {
    private Player player;
    private NPC npc;
    private static Game game;
    private static Stage primaryStage;
    private Region dest;
    public EncounterController(Player player) {
        this.player = player;
    }

    public NPC getNpc() {
        return npc;
    }

    public void handleEncounter(NPC npc, Region destination) {
        this.npc = npc;
        this.dest = destination;
        if (game == null || primaryStage == null) {
            throw new IllegalStateException("You have not yet set up the screen environment!");
        }
        if (this.npc != null) {
            EncounterScreen screen = new EncounterScreen(primaryStage, game, npc);
            screen.display();
        } else {
            NothingHappenedScreen screen =
                    new NothingHappenedScreen(primaryStage, game, dest.getName());
            screen.display();
        }
    }

    public void resumeTravel() {
        player.travelToRegion(dest, true);
    }

    public static void setupScreenEnvironment(Game game, Stage primaryStage) {
        EncounterController.game = game;
        EncounterController.primaryStage = primaryStage;
    }
}
