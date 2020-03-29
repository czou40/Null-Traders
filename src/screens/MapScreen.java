package screens;

import cores.NPCEncounters.EncounterController;
import cores.places.Region;
import cores.places.Universe;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import uicomponents.*;
import cores.Game;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class MapScreen extends Screen {
    private static VisualizedUniverseMap map;
    private Screen from;

    public MapScreen(Stage primaryStage, Game game) {
        this(primaryStage, game, new CharacterSheetScreen(primaryStage, game));
    }

    public MapScreen(Stage primaryStage, Game game, Screen from) {
        super(primaryStage, game);
        this.from = from;
        EncounterController.setupScreenEnvironment(game, primaryStage);
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
        String spaceshipImage = game.getPlayer().getShip().getImage();
        if (map == null) {
            map = new VisualizedUniverseMap(game.getUniverse(),
                    getRootWidth(), getRootHeight(), getPrimaryStage(), spaceshipImage);
        } else {
            map.updateSpaceshipImage(spaceshipImage);
            map.errorMessageProperty().set("");
            map.updateWidthAndHeightProperty(getRootWidth(), getRootHeight());
        }
        addToRoot(map);
        MyNavigationButton back = new MyNavigationButton("Back", from);
        back.layoutXProperty().bind(getRootWidth().subtract(back.widthProperty()).subtract(50));
        //System.out.println(back.layoutXProperty().get());
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

        back.setVisible(!map.isTraveling());
        fuelLabel.setVisible(!map.isTraveling());
        errorLabel.setVisible(false);
        addToRoot(back);
        addToRoot(fuelLabel);
        addToRoot(errorLabel);

        if (game.getPlayer().hasCompass()) {
            Compass compass = new Compass();
            updateCompass(compass);
            game.getUniverse().currentRegionProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        updateCompass(compass);
                    });
            compass.layoutXProperty().bind(
                    getRootWidth().subtract(compass.widthProperty()).subtract(50));
            compass.layoutYProperty().bind(
                    getRootHeight().subtract(compass.heightProperty()).subtract(50));
            compass.setVisible(!map.isTraveling());
            map.isTravelingProperty().addListener((observable, oldValue, newValue) -> {
                compass.setVisible(!newValue);
            });
            addToRoot(compass);
        }
    }

    private void updateCompass(Compass compass) {
        Region ultimate = game.getUniverse().getUltimateRegion();
        if (game.getUniverse().getCurrentRegion().equals(ultimate)) {
            compass.stopWorking();
        } else {
            compass.setAngle(game.getUniverse().calculateAngleToUltimateRegion());
        }
    }

    public static void clearCache() {
        MapScreen.map = null;
    }
}