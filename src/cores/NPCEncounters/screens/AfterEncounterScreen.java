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
    private boolean gameOver;


    /**
     * Constructs a new instance.
     *
     * @param primaryStage The primary stage
     * @param game         The game
     * @param controller   The controller
     */
    public AfterEncounterScreen(Stage primaryStage, Game game, EncounterController controller) {
        super(primaryStage, game);
        this.controller = controller;
        whatWillHappenLater = "You will return to your place of departure.";
    }

    public void setDestination(String destination) {
        this.destination = destination;
        if (!gameOver) {
            whatWillHappenLater = destination == null
                    ? "You will return to your place of departure."
                    : "You will soon arrive at " + destination + ".";
        }
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        if (gameOver) {
            whatWillHappenLater = "Your ship is completely destroyed.";
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Pane constructRoot() {
        label = new Label();
        okButton = new Button("OK");
        okButton.setOnAction(event -> {
            if (gameOver) {
                controller.handleGameOver();
            } else {
                controller.notifyMapController();
                this.close();
            }
        });
        label.setText((message == null || message.equals("") ? "" : message + "\n")
                + whatWillHappenLater);
        return new VBox(20, label, okButton);
    }
}
