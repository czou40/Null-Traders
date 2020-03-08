package screens;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import uicomponents.*;
import cores.Game;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;

public class MapScreen extends Screen {
    private static VisualizedUniverseMap map;
    private Screen from;

    public MapScreen(Stage primaryStage, Game game) {
        this(primaryStage, game, null);
    }

    public MapScreen(Stage primaryStage, Game game, Screen from) {
        super(primaryStage, game);
        this.from = from;
    }

    @Override
    public Pane constructRoot() {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color:#000020;");
        pane.getStylesheets().addAll("styles/general.css");
        return pane;
    }

    @Override
    public void doAfterScreenIsShown() {
        if (map == null) {
            map = new VisualizedUniverseMap(game.getUniverse(),
                    getRootWidth(), getRootHeight(), getPrimaryStage());
        } else {
            map.updateWidthAndHeightProperty(getRootWidth(), getRootHeight());
        }
        addToRoot(map);
        MyNavigationButton back = new MyNavigationButton("Back", from);
        back.visibleProperty().bind(map.isTravelingProperty().not());
        addToRoot(back);
        back.layoutXProperty().bind(getRootWidth().subtract(back.widthProperty()).subtract(50));
        System.out.println(back.layoutXProperty().get());
        back.layoutYProperty().set(50);
        Label fuelLabel = new Label();
        fuelLabel.layoutXProperty().set(50);
        fuelLabel.layoutYProperty().set(50);
        fuelLabel.textProperty().bind(
                Bindings.format("Ship Fuel: %d", getGame().getPlayer().getShip().fuelProperty())
        );
        addToRoot(fuelLabel);
    }
}