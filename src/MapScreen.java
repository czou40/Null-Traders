import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MapScreen extends GameScreen {
    public MapScreen(Stage primaryStage, Game game) {
        super(primaryStage, game, "Map of Universe", true);
    }

    @Override
    public Pane constructContentPane() {
        return new Pane();
    }
}
