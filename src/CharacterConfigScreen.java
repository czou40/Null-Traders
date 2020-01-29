import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CharacterConfigScreen extends Screen {
    public CharacterConfigScreen(Stage primaryStage) {
        super(primaryStage);
    }

    @Override
    public Scene constructScene() {
        Label test = new Label("This is the character config screen!");
        return new Scene(new StackPane(test),800,600);
    }
}
