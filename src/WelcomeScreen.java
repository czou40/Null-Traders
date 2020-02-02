import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WelcomeScreen extends Screen {

    public WelcomeScreen(Stage primaryStage, Game game) {
        super(primaryStage, game);
    }

    public Scene constructScene() {
        Button welcomeButton = new Button("New Game!");
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
        welcomeScene.getStylesheets().addAll("styles/general.css", "styles/welcome-screen.css");
        return welcomeScene;
    }
}
