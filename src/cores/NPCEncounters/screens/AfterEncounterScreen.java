package cores.NPCEncounters.screens;

import cores.Game;
import cores.NPCEncounters.EncounterController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import screens.Screen;

public class AfterEncounterScreen extends Screen {
    private String destination;
    private Label label;
    private Button okButton;
    private String message;
    private String whatWillHappenLater;
    private EncounterController controller;


    /**
     * Constructs a new instance.
     *
     * @param primaryStage The primary stage
     * @param game         The game
     */
    public AfterEncounterScreen(Stage primaryStage, Game game, EncounterController controller) {
        super(primaryStage, game);
        this.controller = controller;
        whatWillHappenLater = "\nYou will return to your place of departure.";
        label = new Label();
        okButton = new Button("OK");
        okButton.setOnAction(event -> {
            controller.goBackToMapScreen();
        });
    }

    public void setDestination(String destination) {
        this.destination = destination;
        whatWillHappenLater = destination == null ?
                "\nYou will return to your place of departure."
                : "\nYou will soon arrive at " + destination + ".";
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Pane constructRoot() {
        label.setText(message + whatWillHappenLater);
        return new VBox(label, okButton);
    }
}
