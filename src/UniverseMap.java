import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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

    public UniverseMap() {
        regions = new Vector<>();
        canvas = new MapCanvas(800, 600);
        generateRegions();
    }

    public Pane getVisualizedMap(ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty) {
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

    public Region getRandomRegion() {
        System.out.println(regions.size());
        int randIndex = (int) (Math.random() * regions.size());
        return regions.get(randIndex);
    }

    private void generateRegions() {
        Vector<RegionData> regionData = new Vector<>();
        for (RegionData data : RegionData.values()) {
            regionData.add(data);
        }

        while (regionData.size() > 0) {
            RegionData randRegion = regionData.remove((int) Math.random() * regionData.size());
            regions.add(new Region(randRegion, (int) (Math.random() * canvas.getWidth()), (int) (Math.random() * canvas.getHeight()), false));
        }
    }

    public Vector<MapDot> getDots() {
        Vector<MapDot> result = new Vector<>();
        for (Region region : regions) {
            result.add(new MapDot(region));
        }
        return result;
    }


    public void drawCanvas() {
        canvas.draw();
    }

    private class MapDot extends Circle {
        private Region region;

        public MapDot(Region region) {
            super(region.getX(), region.getY(), 5, Color.WHITE);
            Color dotColor;
            if (region.isFound()) {
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

    private class MapCanvas extends Canvas {
        public MapCanvas(int i, int i1) {
            super(i, i1);
            draw();
            widthProperty().addListener(e -> {
                draw();
            });
            heightProperty().addListener(e -> {
                draw();
            });
        }

        @Override
        public boolean isResizable() {
            return true;
        }

        public void draw() {
            GraphicsContext graphicsContext = this.getGraphicsContext2D();
            System.out.println(regions.size());
            for (int i = 0; i < regions.size(); i++) {
                Region region = regions.get(i);

                Circle circle;
                circle = new Circle(region.getX(), region.getY(), 10, Color.WHITE);
                circle.setId(String.valueOf(i));
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillOval(region.getX() - 5,region.getY() - 5, 10,10);
            }
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
