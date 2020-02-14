import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.util.Vector;

/**
 * This is a collection of regions.
 * Also, it has a getCanvas method that returns a canvas.
 */
public class UniverseMap {
    private Vector<Region> regions;
    private MapCanvas canvas;
    private ReadOnlyDoubleProperty widthProperty;
    private ReadOnlyDoubleProperty heightProperty;

    public UniverseMap(ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty) {
        this.widthProperty = widthProperty;
        this.heightProperty = heightProperty;
        regions = new Vector<>();
        canvas = new MapCanvas(widthProperty.get(), heightProperty.get());
        generateRegions();
    }

    public Pane getVisualizedMap() {
        Pane visualizedMap = new Pane();
        canvas.widthProperty().bind(widthProperty);
        canvas.heightProperty().bind(heightProperty);
        visualizedMap.getChildren().add(canvas);
        for (MapDot dot: getDots()) {
            visualizedMap.getChildren().add(dot);
        }
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(widthProperty);
        clip.heightProperty().bind(heightProperty);
        visualizedMap.setClip(clip);
        return visualizedMap;
    }

    private void generateRegions() {
        for (RegionData i : RegionData.values()) {
            int x = (int) (Math.random() * widthProperty.get());
            int y = (int) (Math.random() * heightProperty.get());
            regions.add(new Region(i, x, y, false));
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

    private static class MapDot extends Circle {
        private Region region;

        public MapDot(Region region) {
            super(region.getX(), region.getY(), 5, Color.WHITE);
            Color dotColor;
            if (region.isFound()) {
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
                alert.setContentText(region.getName());
                alert.show();
            });
        }

        public Region getRegion() {
            return region;
        }
    }

    private static class MapCanvas extends Canvas {
        public MapCanvas(double i, double i1) {
            super(i, i1);
            widthProperty().addListener(e -> {
            });
            heightProperty().addListener(e -> {
            });
        }

        @Override
        public boolean isResizable() {
            return true;
        }

        @Override
        public double minWidth(double height) {
            return 0.0;
        }

        @Override
        public double minHeight(double width) {
            return 0.0;
        }

        @Override
        public double maxHeight(double width) {
            return 9999;
        }

        @Override
        public double maxWidth(double height) {
            return 9999;
        }
    }
}
