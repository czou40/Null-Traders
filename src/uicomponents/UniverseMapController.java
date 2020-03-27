package uicomponents;

import cores.NPCEncounters.EncounterController;
import cores.places.Region;
import cores.places.Universe;
import javafx.animation.Timeline;
import javafx.stage.Stage;

public class UniverseMapController {
    VisualizedUniverseMap map;
    Universe universe;
    Stage primaryStage;

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

