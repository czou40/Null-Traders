package screens;

import cores.Game;
import cores.NPCEncounters.NPC;
import javafx.scene.Parent;
import javafx.stage.Stage;
import screens.EncounterScreen;

public class PoliceScreen extends EncounterScreen {
    /**
     * Constructs a new instance.
     *
     * @param primaryStage The primary stage
     * @param game         The game
     */
    public PoliceScreen(Stage primaryStage, Game game, NPC npc) {
        super(primaryStage, game, npc);
    }

    @Override
    public Parent constructRoot() {
        return null;
    }
}