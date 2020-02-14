import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.util.Vector;

/**
 * This is a collection of regions.
 * Also, it has a getCanvas method that returns a canvas.
 */
public class UniverseMap {
    private Game game;
    private Vector<Region> regions;
    private MapCanvas canvas;
    private Vector<MapDot> dots;
    private ReadOnlyDoubleProperty widthProperty;
    private ReadOnlyDoubleProperty heightProperty;

    public UniverseMap(Game game, ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty) {
        this.game = game;
        this.widthProperty = widthProperty;
        this.heightProperty = heightProperty;
        regions = new Vector<>();
        canvas = new MapCanvas(widthProperty.get(), heightProperty.get());
        generateRegions();
        dots = constructDots();
    }

    public Pane getVisualizedMap() {
        Pane visualizedMap = new Pane();
        canvas.widthProperty().bind(widthProperty);
        canvas.heightProperty().bind(heightProperty);
        visualizedMap.getChildren().add(canvas);
        for (MapDot dot: getDots()) {
            visualizedMap.getChildren().add(dot);
            //visualizedMap.getChildren().add(new RegionDescriptionBox(dot.getRegion(),
            // dot.getCenterX(), dot.getCenterY() - 10));
        }
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(widthProperty);
        clip.heightProperty().bind(heightProperty);
        visualizedMap.setClip(clip);
        return visualizedMap;
    }

    private void generateRegions() {
        System.out.println(widthProperty.get());
        System.out.println(heightProperty.get());
        for (RegionData i : RegionData.values()) {
            int x = (int) (Math.random() * widthProperty.get());
            int y = (int) (Math.random() * heightProperty.get());
            regions.add(new Region(i, x, y, false));
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

    private class MapDot extends Circle {
        private Region region;

        public MapDot(Region region) {

            super(region.getX(), region.getY(), 5, Color.WHITE);

            //FOR TESTING PURPOSES ONLY
            this.setCenterX(region.getX() * 800 / widthProperty.getValue());
            this.setCenterY(region.getY() * 600 / heightProperty.getValue());

            Color dotColor;
            if (game.getCurrentRegion().equals(region)) {
                dotColor = Color.GOLD;
            } else if (region.isFound()) {
                dotColor = Color.WHITE;
            } else {
                dotColor = Color.BLACK;
            }
            this.setFill(dotColor);

            this.region = region;
            this.setCursor(Cursor.HAND);
            this.setOnMouseClicked(e -> {
                game.travelToRegion(region);
            });
        }

        public Region getRegion() {
            return region;
        }
    }

    private class RegionDescriptionBox extends VBox {
        public RegionDescriptionBox(Region region, double x, double y) {
            super();
            Label nameLabel = new Label("Name: " + region.getName());
            nameLabel.setFont(Font.font(5));

            Label descriptionLabel = new Label("Description: " + region.getDescription());
            descriptionLabel.setFont(Font.font(5));
            descriptionLabel.setWrapText(true);

            Label technologyLabel = new Label("Technology Level: " + region.getTechnologyLevel());
            technologyLabel.setFont(Font.font(5));

            this.getChildren().addAll(nameLabel, descriptionLabel, technologyLabel);
            this.setLayoutX(x);
            this.setLayoutY(y);
            this.toBack();
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