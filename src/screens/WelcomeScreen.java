package screens;

import javafx.scene.layout.Pane;
import uicomponents.*;

import cores.Game;
import cores.Main;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * This class describes a welcome screen.
 */
public class WelcomeScreen extends Screen {

    /**
     * Constructs a new instance.
     *
     * @param      primaryStage  The primary stage
     * @param      game          The game
     */
    public WelcomeScreen(Stage primaryStage, Game game) {
        super(primaryStage, game);
        this.setMusicPath("src/sounds/theme.mp3");
    }

    @Override
    public Pane constructRoot() {
        Button welcomeButton = new Button("New Game!");
        welcomeButton.setSkin(new ButtonScaleHover(welcomeButton));
        welcomeButton.getStyleClass().add("button-large");
        welcomeButton.setOnAction(e -> {
            new CharacterConfigScreen(getPrimaryStage(), game).display();
        });
        Label welcomeTitle = new Label("Null-Traders");
        welcomeTitle.getStyleClass().add("h0"); // Apply CSS styles
        Label groupTitle = new Label("The Null Pointers");
        groupTitle.getStyleClass().add("h1"); // Apply CSS styles
        // Demonstration of how to use classes.elements.MyGridPane
        double[] rowConstraints = {25, 15, 15, 15, 30};
        double[] columnConstraints = {100};
        MyGridPane myGridPane = new MyGridPane(rowConstraints,
                columnConstraints, HPos.CENTER, VPos.CENTER);
        myGridPane.addColumn(0, null, welcomeTitle, groupTitle, welcomeButton);
        myGridPane.getStylesheets().addAll("styles/general.css", "styles/clear-background.css");
        return myGridPane;
    }
}
