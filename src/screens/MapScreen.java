package screens;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import uicomponents.*;
import cores.Game;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MapScreen extends GameScreen {
    public MapScreen(Stage primaryStage, Game game) {
        super(primaryStage, game, "MAP OF UNIVERSE", true);

        game.getPlayer().encounterProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                EncounterScreen screen = newValue.getEncounterScreen(game, primaryStage);
                screen.display();
            }
        });
    }

    @Override
    public Pane constructContentPane() {
        return new Pane();
    }

    @Override
    public void doAfterScreenIsShown() {
        addToContentPane(new VisualizedUniverseMap(game.getUniverse(),
                getContentWidth(), getContentHeight(), getPrimaryStage()));

        Label fuelLabel = new Label();
        fuelLabel.textProperty().bind(
                Bindings.format("Ship Fuel: %d", getGame().getPlayer().getShip().fuelProperty())
        );

        addToContentPane(fuelLabel);
    }
}