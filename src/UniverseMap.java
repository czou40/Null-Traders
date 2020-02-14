import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Vector;

/**
 * This is a collection of regions.
 * Also, it has a getCanvas method that returns a canvas.
 */
public class UniverseMap {
    private Vector<Region> regions;
    //private MapCanvas canvas;
    private SimpleDoubleProperty offsetX;
    private SimpleDoubleProperty offsetY;
    private SimpleDoubleProperty scaling;
    private static final int MAP_WIDTH = 1920;
    private static final int MAP_HEIGHT = 1080;
    private Game game;

    public UniverseMap(Game game) {
        this.game = game;
        scaling = new SimpleDoubleProperty(1);
        //canvas = new MapCanvas();
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
        scaling = new SimpleDoubleProperty(1);
        Pane visualizedMap = new Pane();
        offsetX = new SimpleDoubleProperty();
        offsetY = new SimpleDoubleProperty();
        offsetX.bind(widthProperty.divide(2).subtract(game.getCurrentRegion().getX()));
        offsetY.bind(heightProperty.divide(2).subtract(game.getCurrentRegion().getY()));
        Rectangle border = new Rectangle();
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.WHITE);
        border.xProperty().bind(scaling.multiply(-game.getCurrentRegion().getX()).
                add(offsetX).add(game.getCurrentRegion().getX()));
        border.yProperty().bind(scaling.multiply(-game.getCurrentRegion().getY()).
                add(offsetY).add(game.getCurrentRegion().getY()));
        border.widthProperty().bind(scaling.multiply(MAP_WIDTH));
        border.heightProperty().bind(scaling.multiply(MAP_HEIGHT));
        visualizedMap.getChildren().add(border);
        for (MapDot dot: getDots()) {
            visualizedMap.getChildren().add(dot.nameLabel);
            visualizedMap.getChildren().add(dot);
        }
        visualizedMap.setOnScroll(event -> {
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
        NameGenerator nameGenerator = new NameGenerator();
        for (int i = 1; i <= 20; i++) {
            int x = (int) (Math.random() * MAP_WIDTH);
            int y = (int) (Math.random() * MAP_HEIGHT);
            regions.add(new Region(nameGenerator.getName(), "", 1, x, y, false));
        }
    }

    public Region getRandomRegion() {
        return regions.get((int) Math.random() * regions.size());
    }


    public Vector<MapDot> getDots() {
        Vector<MapDot> result = new Vector<>();
        for (Region region : regions) {
            result.add(new MapDot(region));
        }
        return result;
    }

    private class MapDot extends Circle {
        private Region region;
        private Label nameLabel;

        public MapDot(Region region) {
            super(10, 10, 5, Color.WHITE);
            SimpleDoubleProperty scalingX = new SimpleDoubleProperty();
            SimpleDoubleProperty scalingY = new SimpleDoubleProperty();
            scalingX.bind(scaling.multiply(region.getX() - game.getCurrentRegion().getX()));
            scalingY.bind(scaling.multiply(region.getY() - game.getCurrentRegion().getY()));
            this.centerXProperty().bind(scalingX.add(offsetX).add(game.getCurrentRegion().getX()));
            this.centerYProperty().bind(scalingY.add(offsetY).add(game.getCurrentRegion().getY()));
            Color dotColor;
            if (region == game.getCurrentRegion()) {
                dotColor = Color.DEEPSKYBLUE;
            } else if (region.isFound()) {
                System.out.println("Region Found");
                dotColor = Color.WHITE;
            } else {
                dotColor = Color.BLACK;
            }
            this.setFill(dotColor);
            this.region = region;
            this.setCursor(Cursor.HAND);
            this.setOnMouseClicked(e -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(region.getName() + "\n" + region.getDescription()
                + "\nTechnology level: " + region.getTechnologyLevel());
                alert.show();
            });
            nameLabel = new Label(region.getName());
            nameLabel.layoutXProperty().bind(centerXProperty().add(4));
            nameLabel.layoutYProperty().bind(centerYProperty().add(2));
            nameLabel.setStyle("-fx-font-size: 16px;");
        }
    }
}
