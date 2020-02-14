import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.text.Font;
import java.util.Vector;

/**
 * This is a collection of regions.
 * It is a representation to the whole universe.
 * It has a getVisualizedMap method to return a map.
 */
public class Universe {
    private Game game;
    private Vector<Region> regions;
    private Vector<MapDot> dots;
    private SimpleDoubleProperty offsetX; // Used to record how map should be positioned.
    private SimpleDoubleProperty offsetY;
    private SimpleDoubleProperty currentXProperty; //Used to record the center of the map
    private SimpleDoubleProperty currentYProperty;
    private SimpleDoubleProperty scaling;
    private RegionDescriptionBox currentRegionDescriptionBox;
    private static final int MAP_WIDTH = 1920;
    private static final int MAP_HEIGHT = 1080;

    public Universe(Game game) {
        this.game = game;
        regions = new Vector<>();
        scaling = new SimpleDoubleProperty(1);
        generateRegions();
    }

    private void setScaling(double scaling) {
        if (scaling >= 0.5 && scaling <= 3) {
            this.scaling.set(scaling);
        }
    }

    private double getScaling() {
        return scaling.get();
    }

    private SimpleDoubleProperty scalingProperty() {
        return scaling;
    }

    public Pane getVisualizedMap(ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty) {
        Pane visualizedMap = new Pane();
        //if the panel is not big enough, you cannot use zoom on and zoom out outside of the map.
        visualizedMap.setPrefHeight(9999);
        visualizedMap.setPrefWidth(9999);
        currentXProperty = new SimpleDoubleProperty(game.getCurrentRegion().getX());
        currentYProperty = new SimpleDoubleProperty(game.getCurrentRegion().getY());
        offsetX = new SimpleDoubleProperty();
        offsetY = new SimpleDoubleProperty();
        offsetX.bind(widthProperty.divide(2).subtract(currentXProperty));
        offsetY.bind(heightProperty.divide(2).subtract(currentYProperty));
        Rectangle border = new Rectangle();
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.WHITE);
        border.xProperty().bind(scaling.multiply(currentXProperty).multiply(-1).
                                add(offsetX).add(currentXProperty));
        border.yProperty().bind(scaling.multiply(currentYProperty).multiply(-1).
                add(offsetY).add(currentYProperty));
        border.widthProperty().bind(scaling.multiply(MAP_WIDTH));
        border.heightProperty().bind(scaling.multiply(MAP_HEIGHT));
        visualizedMap.getChildren().add(border);

        dots = constructDots();
        for (MapDot dot: getDots()) {
            visualizedMap.getChildren().add(dot.nameLabel);
            visualizedMap.getChildren().add(dot);
//            visualizedMap.getChildren().add(new RegionDescriptionBox(game, dot.region,
//             dot.getCenterX(), dot.getCenterY() - 10));
        }
        currentRegionDescriptionBox = new RegionDescriptionBox(game, game.getCurrentRegion(),
                widthProperty.get() - 300, heightProperty.get() - 200);
//        regionDescriptions = constructRegionDescriptions();
//        for (RegionDescriptionBox box : getRegionDescriptions()) {
//            visualizedMap.getChildren().add(box);
//        }
        currentRegionDescriptionBox.layoutXProperty().bind(widthProperty.
                subtract(currentRegionDescriptionBox.widthProperty()));
        currentRegionDescriptionBox.layoutYProperty().bind(heightProperty.
                subtract(currentRegionDescriptionBox.heightProperty()));
        visualizedMap.getChildren().add(currentRegionDescriptionBox);
        visualizedMap.setOnScroll(event->{
            Timeline timeline = new Timeline();
            KeyValue keyValue = new KeyValue(scaling, scaling.get() * (event.getDeltaY() > 0 ? 1.1 : 0.9));
            KeyFrame keyFrame = new KeyFrame(new Duration(100), keyValue);
            timeline.getKeyFrames().addAll(keyFrame);
            timeline.play();
        });
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(widthProperty);
        clip.heightProperty().bind(heightProperty);
        visualizedMap.setClip(clip);
        return visualizedMap;
    }

    private void generateRegions() {
        regions = new Vector<>();
        for (RegionData i : RegionData.values()) {
            int x = (int) (Math.random() * MAP_WIDTH);
            int y = (int) (Math.random() * MAP_HEIGHT);
            regions.add(new Region(i, x, y, false));
        }
        /*
        NameGenerator nameGenerator = new NameGenerator();
        for (int i = 1; i <= 10; i++) {
            int x = (int) (Math.random() * MAP_WIDTH);
            int y = (int) (Math.random() * MAP_HEIGHT);
            regions.add(new Region(nameGenerator.getName(), "", 1, x, y, false));
        }

         */
    }

    public Region getRandomRegion() {
        return regions.get((int) (Math.random() * regions.size()));
    }


    public Vector<MapDot> constructDots() {
        Vector<MapDot> result = new Vector<>();
        for (Region region : regions) {
            result.add(new MapDot(region));
        }
        return result;
    }

    public Vector<RegionDescriptionBox> constructRegionDescriptions() {
        Vector<RegionDescriptionBox> result = new Vector<>();
        for (MapDot dot : dots) {
            result.add(new RegionDescriptionBox(game, dot.region, dot.getCenterX(), dot.getCenterY() - 10));
        }

        return result;
    }

    public void updateDots() {
        Timeline timeline = new Timeline();
        KeyValue keyValueX = new KeyValue(currentXProperty, game.getCurrentRegion().getX());
        KeyFrame keyFrameX = new KeyFrame(new Duration(1000), keyValueX);
        KeyValue keyValueY = new KeyValue(currentYProperty, game.getCurrentRegion().getY());
        KeyFrame keyFrameY = new KeyFrame(new Duration(1000), keyValueY);
        timeline.getKeyFrames().addAll(keyFrameX, keyFrameY);
        timeline.play();
        for (MapDot dot : dots) {
            Color dotColor;
            if (game.getCurrentRegion().equals(dot.region)) {
                dotColor = Color.GOLD;
            } else if (dot.region.isFound()) {
                dotColor = Color.WHITE;
            } else {
                dotColor = Color.BLACK;
            }
            dot.setFill(dotColor);
        }
        currentRegionDescriptionBox.setRegion(game.getCurrentRegion());
    }

    public Vector<MapDot> getDots() {
        return dots;
    }

    public RegionDescriptionBox getCurrentRegionDescriptionBox() {
        return currentRegionDescriptionBox;
    }

    private class MapDot extends Circle {
        private Region region;
        private Label nameLabel;

        public MapDot(Region region) {
            super(10, 10, 5, Color.WHITE);
            SimpleDoubleProperty scalingX = new SimpleDoubleProperty();
            SimpleDoubleProperty scalingY = new SimpleDoubleProperty();
            scalingX.bind(currentXProperty.multiply(-1).add(region.getX()).multiply(scaling));
            scalingY.bind(currentYProperty.multiply(-1).add(region.getY()).multiply(scaling));
            this.centerXProperty().bind(scalingX.add(offsetX).add(currentXProperty));
            this.centerYProperty().bind(scalingY.add(offsetY).add(currentYProperty));
            Color dotColor;
            if (region == game.getCurrentRegion()) {
                dotColor = Color.GOLD;
            } else if (region.isFound()) {
                System.out.println("Region Found");
                dotColor = Color.WHITE;
            } else {
                dotColor = Color.BLACK;
            }
            this.setFill(dotColor);
            this.region = region;
            this.setCursor(Cursor.HAND);
            this.setOnMouseClicked(e->{
                game.travelToRegion(region);
            });

            if (region.isFound()) {
                nameLabel = new Label(region.getName());
            } else {
                nameLabel = new Label("???");
            }
            nameLabel.layoutXProperty().bind(centerXProperty().add(4));
            nameLabel.layoutYProperty().bind(centerYProperty().add(2));
            region.foundProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    nameLabel.setText(region.getName());
                } else {
                    nameLabel.setText("???");
                }
            });
            nameLabel.setStyle("-fx-font-size: 16px;");
        }
    }
}
