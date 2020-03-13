package screens;

import cores.Game;
import cores.NPCEncounters.NPC;
import javafx.stage.Stage;

public abstract class EncounterScreen extends Screen {
    private NPC npc;
    /**
     * Constructs a new instance.
     *
     * @param primaryStage The primary stage
     * @param game         The game
     */
    public EncounterScreen(Stage primaryStage, Game game, NPC npc) {
        super(primaryStage, game);
        this.npc = npc;
    }
}
