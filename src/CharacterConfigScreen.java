import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CharacterConfigScreen extends Screen {
    private StackPane root;
    private Button welcomeButton;

    public CharacterConfigScreen(Stage primaryStage) {
        super(primaryStage);
        welcomeButton = new Button("This is the config screen!");
        root = new StackPane(welcomeButton);
        setScene(root, 800, 600);
    }
}
