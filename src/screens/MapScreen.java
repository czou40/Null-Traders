package screens;

import uicomponents.*;
import cores.Game;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MapScreen extends GameScreen {
    public MapScreen(Stage primaryStage, Game game) {
        super(primaryStage, game, "MAP OF UNIVERSE", true);
    }

    @Override
    public Pane constructContentPane() {
        return new Pane();
    }

    @Override
    public void doAfterScreenIsShown() {
        addToContentPane(new VisualizedUniverseMap(game.getUniverse(),
                getContentWidth(), getContentHeight(), getPrimaryStage()));
    }
}