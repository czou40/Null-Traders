package screens;

import cores.Game;
import cores.Main;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uicomponents.ButtonScaleHover;
import uicomponents.MyGridPane;

public class VictoryScreen extends Screen {
    /**
     * Constructs a new instance.
     *
     * @param primaryStage The primary stage
     * @param game         The game
     */
    public VictoryScreen(Stage primaryStage, Game game) {
        super(primaryStage, game);
    }

    @Override
    public Pane constructRoot() {
        Button backButton = new Button("Back to main menu");
        backButton.setSkin(new ButtonScaleHover(backButton));
        backButton.getStyleClass().add("button-large");
        backButton.setOnAction(e -> {
            Main.startNewGame(getPrimaryStage());
        });
        Label gameOverLabel = new Label("Victory!");
        gameOverLabel.getStyleClass().add("h0"); // Apply CSS styles
        Label loseTitle = new Label("Your just won the game!");
        loseTitle.getStyleClass().add("h1"); // Apply CSS styles
        // Demonstration of how to use classes.elements.MyGridPane
        double[] rowConstraints = {25, 15, 15, 15, 30};
        double[] columnConstraints = {100};
        MyGridPane myGridPane = new MyGridPane(rowConstraints,
                columnConstraints, HPos.CENTER, VPos.CENTER);
        myGridPane.addColumn(0, null, gameOverLabel, loseTitle, backButton);
        myGridPane.getStylesheets().addAll("styles/general.css", "styles/clear-background.css");
        return myGridPane;
    }
}
