package screens;

import cores.Game;
import cores.NPCEncounters.NPC;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BanditScreen extends EncounterScreen {
    /**
     * Constructs a new instance.
     *
     * @param primaryStage The primary stage
     * @param game         The game
     */
    public BanditScreen(Stage primaryStage, Game game, NPC npc) {
        super(primaryStage, game, npc);
    }

    @Override
    public Pane constructRoot() {
        return null;
    }
}
