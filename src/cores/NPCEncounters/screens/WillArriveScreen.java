package cores.NPCEncounters.Screens;

import cores.Game;
import cores.NPCEncounters.EncounterController;
import cores.places.Region;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import screens.Screen;
import uicomponents.MyGridPane;
import uicomponents.MyNavigationButton;

import java.util.Stack;

public class WillArriveScreen extends Screen {
    private String destination;
    private Label label;
    private Button okButton;
    private String message;
    private EncounterController controller;


    /**
     * Constructs a new instance.
     *
     * @param primaryStage The primary stage
     * @param game         The game
     */
    public WillArriveScreen(Stage primaryStage, Game game, EncounterController controller) {
        super(primaryStage, game);
        this.controller = controller;
        label = new Label();
        okButton = new Button("OK");
        okButton.setOnAction(event -> {
            controller.goBackToMapScreen();
        });
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Pane constructRoot() {
        label.setText(message + "\nYou will soon arrived at " + destination + ".");
        return new VBox(label, okButton);
    }
}
