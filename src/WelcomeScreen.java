import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WelcomeScreen extends Screen {

    public WelcomeScreen(Stage primaryStage, Game game) {
        super(primaryStage, game);
    }

    public Scene constructScene() {
        Button welcomeButton = new Button("This is a test button");
        welcomeButton.setOnAction(e->{
            new CharacterConfigScreen(getPrimaryStage(), game).display();
        });
        Label welcomeTitle = new Label("Null-Traders");
        welcomeTitle.setId("h1"); // Apply CSS styles
        Label groupTitle = new Label("The Null Pointers");
        groupTitle.setId("h2"); // Apply CSS styles
        // Demonstration of how to use MyGridPane
        double[] rowConstraints = {30,10,10,20,30};
        double[] columnConstraints = {100};
        MyGridPane myGridPane = new MyGridPane(rowConstraints,columnConstraints);
        myGridPane.addColumn(0,null,welcomeTitle,groupTitle,welcomeButton);
        Scene welcomeScene = new Scene(myGridPane, 800, 600);
        welcomeScene.getStylesheets().addAll("styles/general.css","styles/welcome-screen.css");
        return welcomeScene;
    }
}
