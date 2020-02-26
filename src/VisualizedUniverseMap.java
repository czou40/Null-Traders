import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Vector;

/**
 * this class is the UI element that represents the universe.
 */
public class VisualizedUniverseMap extends Pane {
    private Universe universe;
    private Vector<MapDot> dots;
    private SimpleDoubleProperty centerX;
    private SimpleDoubleProperty centerY;
    private SimpleDoubleProperty offsetX;
    private SimpleDoubleProperty offsetY;
    private ReadOnlyDoubleProperty widthProperty;
    private ReadOnlyDoubleProperty heightProperty;
    private SimpleDoubleProperty scaling;
    private Stage primaryStage;

    public VisualizedUniverseMap(Universe universe,
                                 ReadOnlyDoubleProperty widthProperty,
                                 ReadOnlyDoubleProperty heightProperty, Stage primaryStage) {
        super();
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

        /*
        Adjust the size of the visualized map according to the given size
        and making the map responsive.
         */
        this.widthProperty = widthProperty;
        this.heightProperty = heightProperty;

        /*
        Set up zooming in and zooming out function.
        When the user scrolls the mouse or uses the touch pad,
        the scaling coefficient will change,
        enabling the user to zoom in or zoom out.
         */
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

        /*
        As the player travels in the universe, its position changes.
        However,
        the current region the player is at should always be at the center of the visualized map.
        That's why we need to calculate where the regions should be displayed on the map.
         */
        centerX = new SimpleDoubleProperty(universe.getCurrentRegion().getX());
        centerY = new SimpleDoubleProperty(universe.getCurrentRegion().getY());
        offsetX = new SimpleDoubleProperty();
        offsetY = new SimpleDoubleProperty();
        offsetX.bind(widthProperty.divide(2).subtract(centerX));
        offsetY.bind(heightProperty.divide(2).subtract(centerY));
        universe.currentRegionProperty().addListener(e -> {
            /*
            Update centerX and centerY information.
            The following codes are equivalent to:
            centerX.set(universe.getCurrentRegion().getX());
            centerY.set(universe.getCurrentRegion().getY());
            We used timeline to make the transition smooth.
             */
            Timeline timeline = new Timeline();
            KeyValue keyValueX = new KeyValue(centerX, universe.getCurrentRegion().getX());
            KeyFrame keyFrameX = new KeyFrame(new Duration(1000), keyValueX);
            KeyValue keyValueY = new KeyValue(centerY, universe.getCurrentRegion().getY());
            KeyFrame keyFrameY = new KeyFrame(new Duration(1000), keyValueY);
            timeline.getKeyFrames().addAll(keyFrameX, keyFrameY);
            timeline.play();
        });

        /*
        Get the primary stage, which allows us to show the region characteristics screen
         */
        this.primaryStage = primaryStage;

        /*
        Construct border of the map
         */
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

        /*
        Construct all the dots.
         */
        dots = new Vector<>();
        for (Region region : universe.getRegions()) {
            MapDot dot = new MapDot(region);
            dots.add(dot);
            //Note! We must add labels first. Otherwise, the labels will cover the dot!
            this.getChildren().addAll(dot.labels, dot);
        }

        /*
        Construct current region description box.
        Since we use binding, the the box automatically updates the information
        of the current regions the player is at as it travels around.
         */
//        currentRegionDescriptionBox = new RegionDescriptionBox(universe.currentRegionProperty());
//        currentRegionDescriptionBox.layoutXProperty().bind(widthProperty.
//                subtract(currentRegionDescriptionBox.widthProperty()));
//        currentRegionDescriptionBox.layoutYProperty().bind(heightProperty.
//                subtract(currentRegionDescriptionBox.heightProperty()));
//        this.getChildren().add(currentRegionDescriptionBox);


        /*
        We clip the map so that we only show a portion of the map,
        preventing it from overflowing on the screen.
         */
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(widthProperty);
        clip.heightProperty().bind(heightProperty);
        this.setClip(clip);
    }

    private class MapDot extends Circle {
        private VBox labels;
        private Region region;

        public MapDot(Region region) {
            super(10, 10, 5, Color.BLACK);

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
            Label nameLabel = new Label(region.isFound() ? region.getName() : "???");
            region.foundProperty().addListener(e -> {
                nameLabel.setText(region.isFound() ? region.getName() : "???");
            });
            nameLabel.setStyle("-fx-font-size: 16px;");

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
            distanceLabel.textProperty().bind(Bindings.format("Distance: %.2f", distance));
            distanceLabel.setStyle("-fx-font-size: 16px;");

            /*
            Wrap the labels in a VBox.
             */
            MyNavigationButton goToRegionChar = new MyNavigationButton("See Characteristics",
                    new RegionCharacteristicsScreen(primaryStage, universe.getPlayer().getGame(), region));
            labels = new VBox();
            labels.getChildren().addAll(nameLabel, distanceLabel, coordinatesLabel, goToRegionChar);
            labels.layoutXProperty().bind(centerXProperty().add(15));
            labels.layoutYProperty().bind(centerYProperty());
            labels.setVisible(false);

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

            /*
            When the user hovers over a dot or the corresponding description box,
            the information is displayed
             */
            this.setOnMouseEntered(e -> {
                labels.setVisible(true);
            });

            labels.setOnMouseEntered(e -> {
                labels.setVisible(true);
            });

            this.setOnMouseExited(e -> {
                labels.setVisible(false);
            });

            labels.setOnMouseExited(e ->{
                labels.setVisible(false);
            });

            /*
            When the player clicks the dots,
            it travels to the region associated with the dots.
             */
            this.setOnMouseClicked(e -> {
                universe.getPlayer().travelToRegion(region);
            });


        }

        private void updateColor() {
            Color dotColor;
            if (region.isCurrentRegion()) {
                dotColor = Color.GOLD;
            } else if (region.isFound()) {
                dotColor = Color.WHITE;
            } else {
                dotColor = Color.BLACK;
            }
            this.setFill(dotColor);
        }
    }
}
