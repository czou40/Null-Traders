package screens;

import cores.Game;
import cores.places.Region;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uicomponents.MyGridPane;
import uicomponents.MyNavigationButton;

import java.util.Stack;

public class NothingHappenedScreen extends Screen {
    String destination;


    /**
     * Constructs a new instance.
     *
     * @param primaryStage The primary stage
     * @param game         The game
     */
    public NothingHappenedScreen(Stage primaryStage, Game game, String destination) {
        super(primaryStage, game);
        this.destination = destination;
    }

    @Override
    public Pane constructRoot() {
        Label info = new Label("During the journey, nothing happened.\n" +
                "You have successfully arrived at " + destination + ".");
        MyNavigationButton button = new MyNavigationButton("OK",
                new MapScreen(getPrimaryStage(), getGame(),
                        new MarketScreen(getPrimaryStage(), getGame(), true)));
        return new VBox(info, button);
    }
}
