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
        super(primaryStage, game, "USE MOUSE OR TOUCH PAD TO ZOOM IN AND OUT!", true);
    }

    @Override
    public Pane constructContentPane() {
        return new Pane();
    }

    @Override
    public void after() {
            addToContentPane(game.getUniverse().getVisualizedMap(getContentWidth(), getContentHeight()));
// ======= now UniverseMap is an attribute in Game and is greated in the game constructor
//         UniverseMap universeMap = new UniverseMap(game, getContentWidth(), getContentHeight());
//         game.setUniverse(universeMap);
//         addToContentPane(universeMap.getVisualizedMap());
// >>>>>>> 7c7a3dc8c69674a861e469de993103802190d07c
    }
}
