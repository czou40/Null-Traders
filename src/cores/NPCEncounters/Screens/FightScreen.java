package cores.NPCEncounters.Screens;

import cores.Game;
import cores.NPCEncounters.EncounterController;
import cores.NPCEncounters.FightableNPC;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import screens.Screen;
import javafx.scene.control.Button;

import java.awt.*;

public class FightScreen extends Screen {
    private FightableNPC npc;
    private EncounterController controller;

    public FightScreen(Stage primaryStage, Game game, FightableNPC npc, EncounterController controller) {
        super(primaryStage, game);
        this.npc = npc;
        this.controller = controller;
    }

    @Override
    public Pane constructRoot() {
        Button button = new Button("This feature is not implemented."
                + " Click to go back.");
        button.setOnAction(event -> {
            controller.handleResumeTravel("");
        });
        return new StackPane(button);
    }
}
