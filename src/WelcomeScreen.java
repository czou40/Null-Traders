import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WelcomeScreen extends Screen {
    private StackPane root;
    private Button welcomeButton;

    public WelcomeScreen(Stage primaryStage) {
        super(primaryStage);
        welcomeButton = new Button("Welcome!");
        welcomeButton.setOnAction(e -> {
            //change scene
            CharacterConfigScreen c = new CharacterConfigScreen(primaryStage);
            try {
                primaryStage.setScene(new CharacterConfigScreen(primaryStage).getScene());
            } catch (SceneNotInitialized sceneNotInitialized) {
                sceneNotInitialized.printStackTrace();
            }
        });
        root = new StackPane(welcomeButton);
        setScene(root, 800, 600);
    }

    public void startGame() {
        welcomeButton.setText("Game started");
    }
}
