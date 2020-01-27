import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WelcomeScreen extends Screen {
    private StackPane root;
    private Button welcomeButton;

    public WelcomeScreen() {
        welcomeButton = new Button("This is a test button");
        welcomeButton.setOnAction(e -> {
            startGame();
        });
        root = new StackPane(welcomeButton);

        setScene(root, 800, 600);
    }

    public void startGame() {
        welcomeButton.setText("Game started");
    }
}
