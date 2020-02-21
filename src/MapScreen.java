import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MapScreen extends GameScreen {
    public MapScreen(Stage primaryStage, Game game) {
        super(primaryStage, game, "USE MOUSE OR TOUCH PAD TO ZOOM IN AND OUT!", true);
    }

    @Override
    public Pane constructContentPane() {
        return new Pane();
    }

    @Override
    public void adjustUIAfterScreenIsShown() {
        addToContentPane(new VisualizedUniverseMap(game.getUniverse(),
                getContentWidth(), getContentHeight()));
    }
}