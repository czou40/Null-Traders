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
            map.errorMessageProperty().set("");
            map.updateWidthAndHeightProperty(getRootWidth(), getRootHeight());
        }
        addToRoot(map);
        MyNavigationButton back = new MyNavigationButton("Back", from);
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
        Label errorLabel = new Label();
        errorLabel.setVisible(false);
        errorLabel.layoutXProperty().set(50);
        errorLabel.layoutYProperty().set(50);
        map.errorMessageProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                fuelLabel.setVisible(true);
                errorLabel.setVisible(false);
            } else {
                fuelLabel.setVisible(false);
                errorLabel.setText(newValue);
                errorLabel.setVisible(true);
            }
        }));
        map.isTravelingProperty().addListener((observable, oldValue, newValue) -> {
            back.setVisible(!newValue);
            fuelLabel.setVisible(!newValue);
            errorLabel.setVisible(false);
        });
        addToRoot(fuelLabel);
        addToRoot(errorLabel);
    }
}