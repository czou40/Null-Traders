package cores.NPCEncounters.Screens;

import cores.Game;
import cores.NPCEncounters.EncounterController;
import cores.NPCEncounters.RobbableNPC;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import screens.Screen;
import uicomponents.MyNavigationButton;

public class RobScreen extends Screen {

    private RobbableNPC npc;
    private EncounterController controller;

    public RobScreen(Stage primaryStage, Game game, RobbableNPC npc, EncounterController controller) {
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
