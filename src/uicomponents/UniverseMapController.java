package uicomponents;

import cores.MyEvent;
import cores.places.Region;
import cores.places.Universe;
import javafx.animation.Timeline;
import javafx.stage.Stage;

public class UniverseMapController {
    VisualizedUniverseMap map;
    Universe universe;
    Stage primaryStage;
    private boolean hasAddedEventHandler;

    public UniverseMapController(Universe universe, VisualizedUniverseMap map, Stage primaryStage) {
        this.universe = universe;
        this.map = map;
        this.primaryStage = primaryStage;
    }

    public void handleTravelEvent(Region departure, Region dest) {
        double ninetyPercentX = departure.getX() * 0.1 + dest.getX() * 0.9;
        double ninetyPercentY = departure.getY() * 0.1 + dest.getY() * 0.9;
        Timeline timeline = new Timeline();
        map.visualizeTravelTo(ninetyPercentX, ninetyPercentY, timeline);
        timeline.setOnFinished(event -> {
            universe.getPlayer().startTravelToRegion(dest);
        });
        if (!hasAddedEventHandler) {
            primaryStage.addEventHandler(MyEvent.ENCOUNTER_FINISHED, event -> {
                handleTravelAfterEncounterFinished(dest);
            });
            hasAddedEventHandler = true;
        }
    }

    private void handleTravelAfterEncounterFinished(Region dest) {
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

