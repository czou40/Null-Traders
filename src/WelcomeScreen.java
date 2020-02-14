import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

/**
 * This class describes a welcome screen.
 */
public class WelcomeScreen extends Screen {
    private static Media theme;
    static MediaPlayer themePlayer;

    /**
     * Constructs a new instance.
     *
     * @param      primaryStage  The primary stage
     * @param      game          The game
     */
    public WelcomeScreen(Stage primaryStage, Game game) {
        super(primaryStage, game);

        //music
        theme = new Media(new File("out/production/cs2340/sounds/theme.mp3").toURI().toString());
        themePlayer = new MediaPlayer(theme);
        themePlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    /**
     * { function_description }
     *
     * @return     { description_of_the_return_value }
     */
    public Scene constructScene() {
        //play music
        themePlayer.play();

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
        // Demonstration of how to use MyGridPane
        double[] rowConstraints = {25, 15, 15, 15, 30};
        double[] columnConstraints = {100};
        MyGridPane myGridPane = new MyGridPane(rowConstraints,
                columnConstraints, HPos.CENTER, VPos.CENTER);
        myGridPane.addColumn(0, null, welcomeTitle, groupTitle, welcomeButton);
        Scene welcomeScene = new Scene(myGridPane, 1280, 720);
        welcomeScene.getStylesheets().addAll("styles/general.css", "styles/clear-background.css");
        return welcomeScene;
    }
}
