import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Stack;

public class MapScreen extends GameScreen {
    public MapScreen(Stage primaryStage, Game game) {
        super(primaryStage, game, "Map of Universe", true);
    }

    @Override
    public Pane constructContentPane() {
        return new Pane();
    }

    @Override
    public void after() {
        UniverseMap universeMap = new UniverseMap(game, getContentWidth(), getContentHeight());
        game.setUniverse(universeMap);
        addToContentPane(universeMap.getVisualizedMap());
    }
}