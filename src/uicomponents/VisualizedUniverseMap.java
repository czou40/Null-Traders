package uicomponents;

import cores.places.Region;
import cores.places.Universe;
import javafx.animation.PauseTransition;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import screens.RegionCharacteristicsScreen;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * this class is the UI element that represents the universe.
 */
public class VisualizedUniverseMap extends Pane {
    private Universe universe;
    private HashMap<Region, MapDot> dots;
    private Line route;
    private SimpleDoubleProperty centerX;
    private SimpleDoubleProperty centerY;
    private SimpleDoubleProperty offsetX;
    private SimpleDoubleProperty offsetY;
    private SimpleDoubleProperty mapWidth;
    private SimpleDoubleProperty mapHeight;
    private SimpleDoubleProperty scaling;
    private Stage primaryStage;
    private SimpleBooleanProperty isTraveling;
    private ImageView spaceShip;
    private SimpleStringProperty errorMessage;

    public VisualizedUniverseMap(Universe universe,
                                 ReadOnlyDoubleProperty widthProperty,
                                 ReadOnlyDoubleProperty heightProperty, Stage primaryStage) {
        super();

        this.isTraveling = new SimpleBooleanProperty(false);
        this.errorMessage = new SimpleStringProperty();
        /*
        Adjust the size of scrollable area.
        if the panel is not big enough, you cannot use zoom on and zoom out outside of the map.
         */
        this.setPrefWidth(9999);
        this.setPrefHeight(9999);

        /*
        Set up the universe.
         */
        this.universe = universe;
        setupMapAutosize(widthProperty, heightProperty);
        setUpZooming();
        setUpMapAutoCenteredAtCurrentRegion();

        /*
        Get the primary stage, which allows us to show the region characteristics screen
         */
        this.primaryStage = primaryStage;

        addBorderToMap();
        addDotsToMap();
        makeDotsTwinkle();
        clipMapToHideOverflow();

        this.route = new Line();
        this.spaceShip = new ImageView("file:src/images/ship.png");
        this.spaceShip.setFitWidth(20);
        this.spaceShip.setFitHeight(20);
        this.spaceShip.xProperty().bind(this.mapWidth.divide(2).subtract(10));
        this.spaceShip.yProperty().bind(this.mapHeight.divide(2).subtract(10));
    }

    /**
     * We clip the map so that we only show a portion of the map,
     * preventing it from overflowing on the screen.
     */
    private void clipMapToHideOverflow() {
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(mapWidth);
        clip.heightProperty().bind(mapHeight);
        this.setClip(clip);
    }

    /**
     * Make dots twinkle on the map. This makes the map visually appealing.
     */
    private void makeDotsTwinkle() {
        PauseTransition twinkle = new PauseTransition(Duration.millis(50));
        twinkle.setOnFinished(e -> {
            for (MapDot dot : dots.values()) {
                dot.light += (dot.increaseInLightIntensity ? 1 : -1) * Math.random() * 0.1;
                if (dot.light >= 1) {
                    dot.increaseInLightIntensity = false;
                    dot.light = 1;
                } else if (dot.light < 0) {
                    dot.increaseInLightIntensity = true;
                    dot.light = 0;
                }
                dot.setOpacity(dot.light);
            }
            twinkle.playFromStart();
        });
        twinkle.play();
    }

    /**
     * Construct all the dots.
     */
    private void addDotsToMap() {
        dots = new HashMap<>();
        for (Region region : universe.getRegions()) {
            MapDot dot = new MapDot(region);
            dots.put(region, dot);
            //Note! We must add labels first. Otherwise, the labels will cover the dot!
            this.getChildren().addAll(dot.nameLabel, dot.infoLabels, dot);

        }
    }

    /**
     * Construct border of the map
     */
    private void addBorderToMap() {
        Rectangle border = new Rectangle();
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.WHITE);
        border.xProperty().bind(scaling.multiply(centerX).multiply(-1).
                add(offsetX).add(centerX));
        border.yProperty().bind(scaling.multiply(centerY).multiply(-1).
                add(offsetY).add(centerY));
        border.widthProperty().bind(scaling.multiply(Universe.WIDTH));
        border.heightProperty().bind(scaling.multiply(Universe.HEIGHT));
        this.getChildren().add(border);
    }

    /**
     * As the player travels in the universe, its position changes.
     * However,
     * the current region the player is at should always be at the center of the visualized map.
     * That's why we need to calculate where the regions should be displayed on the map.
     */
    private void setUpMapAutoCenteredAtCurrentRegion() {
        centerX = new SimpleDoubleProperty(universe.getCurrentRegion().getX());
        centerY = new SimpleDoubleProperty(universe.getCurrentRegion().getY());
        offsetX = new SimpleDoubleProperty();
        offsetY = new SimpleDoubleProperty();
        offsetX.bind(mapWidth.divide(2).subtract(centerX));
        offsetY.bind(mapHeight.divide(2).subtract(centerY));
        universe.currentRegionProperty().addListener((observableValue, oldValue, newValue) -> {
            /*
            Update centerX and centerY information.
            The following codes are equivalent to:
            centerX.set(universe.getCurrentRegion().getX());
            centerY.set(universe.getCurrentRegion().getY());
            We used timeline to make the transition smooth.
             */
            visualizeTravelTo(universe.getCurrentRegion().getX(),
                    universe.getCurrentRegion().getY(),
                    true, newValue);
        });
    }

    /**
     * Set up zooming in and zooming out function.
     * When the user scrolls the mouse or uses the touch pad,
     * the scaling coefficient will change,
     * enabling the user to zoom in or zoom out.
     */
    private void setUpZooming() {
        scaling = new SimpleDoubleProperty(1);
        this.setOnScroll(event -> {
            /*
             * The following codes are equivalent to
             * scaling.set(scaling.get() * (event.getDeltaY() > 0 ? 1.1 : 0.9));
             * We use a timeline to make the transition smooth.
             */
            Timeline timeline = new Timeline();
            KeyValue keyValue = new KeyValue(
                    scaling, scaling.get() * (event.getDeltaY() > 0 ? 1.15 : 0.85));
            KeyFrame keyFrame = new KeyFrame(new Duration(250), keyValue);
            timeline.getKeyFrames().addAll(keyFrame);
            timeline.play();
        });
    }

    /**
     * Adjust the size of the visualized map according to the given size
     * and making the map responsive.
     */
    private void setupMapAutosize(ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty) {
        this.mapWidth = new SimpleDoubleProperty();
        this.mapHeight = new SimpleDoubleProperty();
        this.mapWidth.bind(widthProperty);
        this.mapHeight.bind(heightProperty);
    }

    /**
     * @param x
     * @param y
     * @param isReal
     * @param region
     */
    private void visualizeTravelTo(double x, double y, boolean isReal, Region region) {
        double duration = Math.sqrt(Math.pow(centerX.get() - x, 2)
                + Math.pow(centerY.get() - y, 2)) * 5;
        Timeline timeline = new Timeline();
        KeyValue keyValueX = new KeyValue(centerX, x);
        KeyFrame keyFrameX = new KeyFrame(new Duration(duration), keyValueX);
        KeyValue keyValueY = new KeyValue(centerY, y);
        KeyFrame keyFrameY = new KeyFrame(new Duration(duration), keyValueY);
        timeline.getKeyFrames().addAll(keyFrameX, keyFrameY);
        timeline.setOnFinished(event -> {
            if (isReal) {
                VisualizedUniverseMap.this.getChildren().remove(route);
                VisualizedUniverseMap.this.getChildren().remove(spaceShip);
                isTraveling.set(false);
            } else {
                universe.getPlayer().travelToRegion(region, false);
            }
        });
        timeline.play();
    }

    public boolean isTraveling() {
        return isTraveling.get();
    }

    public SimpleBooleanProperty isTravelingProperty() {
        return isTraveling;
    }

    public void updateWidthAndHeightProperty(ReadOnlyDoubleProperty widthProperty,
                                             ReadOnlyDoubleProperty heightProperty) {
        this.mapWidth.bind(widthProperty);
        this.mapHeight.bind(heightProperty);
    }

    public String getErrorMessage() {
        return errorMessage.get();
    }

    public SimpleStringProperty errorMessageProperty() {
        return errorMessage;
    }

    private class MapDot extends Circle {
        private VBox infoLabels;
        private Label nameLabel;
        private Region region;
        private double light = 1.0;
        private boolean increaseInLightIntensity = true;

        public MapDot(Region region){
            super(10, 10, 2, Color.GHOSTWHITE);

            this.region = region;
            /*
            Positioning the Dot in the correct place on the map.
            The position of the dot depends on where the player is currently at
            and the scaling coefficient.
             */
            SimpleDoubleProperty distanceFromCenterX = new SimpleDoubleProperty();
            SimpleDoubleProperty distanceFromCenterY = new SimpleDoubleProperty();
            distanceFromCenterX.bind(centerX.multiply(-1).add(region.getX()).multiply(scaling));
            distanceFromCenterY.bind(centerY.multiply(-1).add(region.getY()).multiply(scaling));
            this.centerXProperty().bind(distanceFromCenterX.add(offsetX).add(centerX));
            this.centerYProperty().bind(distanceFromCenterY.add(offsetY).add(centerY));

            /*
            Set up the name label.
            The name label automatically changes when the found property changes.
             */
            nameLabel = new Label(region.isFound() ? region.getName() : "???");
            region.foundProperty().addListener(e -> {
                nameLabel.setText(region.isFound() ? region.getName() : "???");
                nameLabel.setVisible(region.isFound());
            });
            nameLabel.setStyle("-fx-font-size: 16px;");
            nameLabel.layoutXProperty().bind(centerXProperty().add(15));
            nameLabel.layoutYProperty().bind(centerYProperty().subtract(12));
            nameLabel.setVisible(region.isFound());
            nameLabel.setMouseTransparent(true);

            /*
            Set up the coordinates label
             */
            Label coordinatesLabel = new Label(region.getX() + ", " + region.getY());
            coordinatesLabel.setStyle("-fx-font-size: 16px;");


            /*
            Set up the distance label.
            The distance is automatically calculated.
             */
            SimpleDoubleProperty distance = new SimpleDoubleProperty(
                    region.distanceTo(universe.getCurrentRegion()));
            universe.currentRegionProperty().addListener((observable, oldRegion, newRegion) -> {
                distance.set(region.distanceTo(newRegion));
            });
            Label distanceLabel = new Label();
            distanceLabel.setStyle("-fx-font-size: 16px;");
            distanceLabel.textProperty().bind(Bindings.format("Distance: %.2f", distance));

            /*
            Wrap the labels in a VBox.
             */
            MyNavigationButton goToRegionChar = new MyNavigationButton("See Characteristics",
                    new RegionCharacteristicsScreen(
                            primaryStage, universe.getPlayer().getGame(), region));
            goToRegionChar.setStyle("-fx-font-size: 16px;");
            infoLabels = new VBox();
            infoLabels.getChildren().addAll(distanceLabel, coordinatesLabel, goToRegionChar);
            infoLabels.layoutXProperty().bind(centerXProperty().add(15));
            infoLabels.layoutYProperty().bind(centerYProperty().add(12));
            infoLabels.setVisible(false);

            /*
            Assign the correct color to the dots.
            This is done automatically.
             */
            updateColor();
            region.foundProperty().addListener(e -> {
                updateColor();
            });
            region.isCurrentRegionProperty().addListener(e -> {
                updateColor();
            });

            /*
            When the user hovers over a dot, the cursor becomes a hand.
             */
            this.setCursor(Cursor.HAND);

            EventHandler<MouseEvent> display = event -> {
                nameLabel.setOpacity(1);
                nameLabel.setVisible(true);
                infoLabels.setVisible(true);
            };

            EventHandler<MouseEvent> hide = event -> {
                nameLabel.setVisible(region.isFound());
                infoLabels.setVisible(false);
            };

            /*
            When the user hovers over a dot or the corresponding description box,
            the information is displayed
             */
            this.setOnMouseEntered(display);
            infoLabels.setOnMouseEntered(display);

            this.setOnMouseExited(hide);
            infoLabels.setOnMouseExited(hide);
            nameLabel.setOnMouseExited(hide);

            /*
            When the player clicks the dots,
            it travels to the region associated with the dots.
             */
            this.setOnMouseClicked(e -> {
                if (!isTraveling.get() && !region.equals(universe.getCurrentRegion())) {
                    if (!universe.getPlayer().ableToTravelTo(region)) {
                        errorMessage.set("You don't have enough fuel!");
                        return;
                    }
                    errorMessage.set("");
                    isTraveling.set(true);
                    route.startXProperty().bind(centerXProperty());
                    route.startYProperty().bind(centerYProperty());
                    route.endXProperty().bind(dots.get(universe.getCurrentRegion()).centerXProperty());
                    route.endYProperty().bind(dots.get(universe.getCurrentRegion()).centerYProperty());
                    route.setStroke(Color.GOLD);
                    route.setOpacity(0.5);
                    route.setStrokeWidth(1);
                    double diffY = route.getStartY() - route.getEndY();
                    double diffX = route.getStartX() - route.getEndX();
                    double angle;
                    if (diffX == 0) {
                        angle = diffY > 0 ? 90 : -90;
                    } else {
                        angle = Math.atan(diffY / diffX) / Math.PI * 180;
                    }
                    spaceShip.setRotate(angle + (diffX < 0 ? 90 : -90));
                    //System.out.println(angle);
                    VisualizedUniverseMap.this.getChildren().add(route);
                    VisualizedUniverseMap.this.getChildren().add(spaceShip);
                    visualizeTravelTo(
                            (universe.getCurrentRegion().getX() + region.getX()) / 2,
                            (universe.getCurrentRegion().getY() + region.getY()) / 2, false, region);
                }
            });

            scaling.addListener(((observable, oldValue, newValue) -> {
                double s = newValue.doubleValue();
                if (s > 1.0 / 3) {
                    setRadius(s * 3);
                } else {
                    setRadius(1);
                }
                if (s < 0.25) {
                    nameLabel.setOpacity(Math.max(s * 10 - 1.5, 0));
                }
            }));
        }

        private void updateColor() {
            Color dotColor;
            if (region.isCurrentRegion()) {
                dotColor = Color.GOLD;
            } else if (region.isFound()) {
                dotColor = Color.LIGHTSKYBLUE;
            } else {
                dotColor = Color.GHOSTWHITE;
            }
            this.setFill(dotColor);
        }
    }
}
