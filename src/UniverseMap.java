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
>>> >>> > 7c7a3dc8c69674a861e469de993103802190d07c

import java.util.Vector;

/**
 * This is a collection of regions.
 * Also, it has a getCanvas method that returns a canvas.
 */
public class UniverseMap {
    private Game game;
    private Vector<Region> regions;
    private Vector<MapDot> dots;
    private Vector<RegionDescriptionBox> regionDescriptions;
    private ReadOnlyDoubleProperty widthProperty;
    private ReadOnlyDoubleProperty heightProperty;
    private SimpleDoubleProperty offsetX;
    private SimpleDoubleProperty offsetY;
    private SimpleDoubleProperty scaling;
    private static final int MAP_WIDTH = 1920;
    private static final int MAP_HEIGHT = 1080;

    public UniverseMap(Game game, ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty) {
        this.game = game;
        this.widthProperty = widthProperty;
        this.heightProperty = heightProperty;
        regions = new Vector<>();
        generateRegions();
        dots = constructDots();
        regionDescriptions = constructRegionDescriptions();
        scaling = new SimpleDoubleProperty(1);
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
        for (RegionDescriptionBox box : getRegionDescriptions()) {
            visualizedMap.getChildren().add(box);
        }
        for (MapDot dot: getDots()) {
            //visualizedMap.getChildren().add(dot.nameLabel);
            visualizedMap.getChildren().add(dot);
            //visualizedMap.getChildren().add(new RegionDescriptionBox(dot.getRegion(),
            // dot.getCenterX(), dot.getCenterY() - 10));
        }
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
        System.out.println(widthProperty.get());
        System.out.println(heightProperty.get());
        for (RegionData i : RegionData.values()) {
            int x = (int) (Math.random() * MAP_WIDTH);
            int y = (int) (Math.random() * MAP_HEIGHT);
            regions.add(new Region(i, x, y, false));
        }
        NameGenerator nameGenerator = new NameGenerator();
        for (int i = 1; i <= 10; i++) {
            int x = (int) (Math.random() * MAP_WIDTH);
            int y = (int) (Math.random() * MAP_HEIGHT);
            regions.add(new Region(nameGenerator.getName(), "", 1, x, y, false));
        }
        game.setCurrentRegion(getRandomRegion());
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
            result.add(new RegionDescriptionBox(game, dot.getRegion(), dot.getCenterX(), dot.getCenterY() - 10));
        }

        return result;
    }

    public void updateDots() {
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
    }

    public Vector<MapDot> getDots() {
        return dots;
    }

    public Vector<RegionDescriptionBox> getRegionDescriptions() {
        return regionDescriptions;
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
// =======
//
//             super(region.getX(), region.getY(), 5, Color.WHITE);
//
//             //FOR TESTING PURPOSES ONLY
//             this.setCenterX(region.getX() * 800 / widthProperty.getValue());
//             this.setCenterY(region.getY() * 600 / heightProperty.getValue());
//
//             Color dotColor;
//             if (game.getCurrentRegion().equals(region)) {
//                 dotColor = Color.GOLD;
//             } else if (region.isFound()) {
// >>>>>>> 7c7a3dc8c69674a861e469de993103802190d07c
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
            nameLabel = new Label(region.getName());
            nameLabel.layoutXProperty().bind(centerXProperty().add(4));
            nameLabel.layoutYProperty().bind(centerYProperty().add(2));
            nameLabel.setStyle("-fx-font-size: 16px;");
        }
    }
}
