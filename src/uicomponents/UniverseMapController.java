package uicomponents;

import cores.NPCEncounters.EncounterController;
import cores.places.Region;
import cores.places.Universe;
import javafx.animation.Timeline;
import javafx.stage.Stage;

public class UniverseMapController {
    private VisualizedUniverseMap map;
    private Universe universe;

    public VisualizedUniverseMap getMap() {
        return map;
    }

    public void setMap(VisualizedUniverseMap map) {
        this.map = map;
    }

    public Universe getUniverse() {
        return universe;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public UniverseMapController(Universe universe, VisualizedUniverseMap map, Stage primaryStage) {
        this.universe = universe;
        this.map = map;
        this.primaryStage = primaryStage;
        EncounterController.setMapController(this);
    }

    public void handleTravelEvent(Region departure, Region dest) {
        double ninetyPercentX = departure.getX() * 0.1 + dest.getX() * 0.9;
        double ninetyPercentY = departure.getY() * 0.1 + dest.getY() * 0.9;
        Timeline timeline = new Timeline();
        map.visualizeTravelTo(ninetyPercentX, ninetyPercentY, timeline);
        timeline.setOnFinished(event -> {
            universe.getPlayer().startTravelToRegion(dest);
        });
    }

    public void handleTravelAfterEncounterFinished(Region dest) {
        double currentX = universe.getCurrentRegion().getX();
        double currentY = universe.getCurrentRegion().getY();
        if (!universe.getCurrentRegion().equals(dest)) {
            map.reverseSpaceshipDirection();
        }
        Timeline timeline = new Timeline();
        map.visualizeTravelTo(currentX, currentY, timeline);
        timeline.setOnFinished(event -> {
            map.stopTravelVisualization();
        });
    }
}

