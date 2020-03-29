package cores.places;

import cores.characters.Player;
import cores.utilities.NameGenerator;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Vector;

/**
 * This class represents the space in which the game take place.
 */
public class Universe {
    private Player player;
    private Vector<Region> regions;
    private Region ultimateRegion;
    private SimpleObjectProperty<Region> currentRegion;
    public static final int WIDTH = 15000;
    public static final int HEIGHT = 15000;
    private static final int REGION_NUMBER = 900;

    public Universe(Player player) {
        this.player = player;
        regions = new Vector<>();
        generateRegions();
        currentRegion = new SimpleObjectProperty<>();
        currentRegion.bind(player.currentRegionProperty());
    }

    private void generateRegions() {
        Vector<Integer> spotsLeft = new Vector<>();
        for (int i = 0; i < REGION_NUMBER; i++) {
            spotsLeft.add(i);
        }

        RegionData[] regionData = RegionData.values();
        final int numRows = (int) (HEIGHT / Math.sqrt(WIDTH * HEIGHT * 1.0 / REGION_NUMBER));
        for (RegionData r : regionData) {
            int spot = spotsLeft.remove((int) (Math.random() * spotsLeft.size()));
            int spotX = spot % (REGION_NUMBER / numRows); //0 to number of regions / 2
            int spotY = spot / (REGION_NUMBER / numRows);   //0 or 1
            int spotWidth = WIDTH / (REGION_NUMBER / numRows);
            int spotHeight = HEIGHT / numRows;
            //regions must be a certain dist from the borders.
            int x = (int) ((spotX + Math.random() * 0.8 + 0.1) * spotWidth);
            int y = (int) ((spotY + Math.random() * 0.8 + 0.1) * spotHeight);
            regions.add(new Region(r, x, y, player));
        }
        NameGenerator nameGenerator = new NameGenerator();
        for (int i = regionData.length; i < REGION_NUMBER; i++) {
            int spot = spotsLeft.remove((int) (Math.random() * spotsLeft.size()));
            int spotX = spot % (REGION_NUMBER / numRows); //0 to number of regions / 2
            int spotY = spot / (REGION_NUMBER / numRows);   //0 or 1
            int spotWidth = WIDTH / (REGION_NUMBER / numRows);
            int spotHeight = HEIGHT / numRows;
            //regions must be a certain dist from the borders.
            int x = (int) ((spotX + Math.random() * 0.8 + 0.1) * spotWidth);
            int y = (int) ((spotY + Math.random() * 0.8 + 0.1) * spotHeight);
            regions.add(new Region(nameGenerator.getName(),
                    "", (int) (Math.random() * 10) + 1, x, y, player));
        }
        ultimateRegion = getRandomRegion();
        ultimateRegion.setAsUltimateRegion();
    }

    public Region getRandomRegion() {
        return regions.get((int) (Math.random() * regions.size()));
    }

    public Region getUltimateRegion() {
        return ultimateRegion;
    }

    public double calculateAngleToUltimateRegion() {
        if (ultimateRegion.equals(currentRegion.get())) {
            throw new ArithmeticException("You are already at the ultimate region!");
        }
        return calculateAngle(currentRegion.get().getX(), currentRegion.get().getY(),
                ultimateRegion.getX(), ultimateRegion.getY());
    }

    private static double calculateAngle(double x1, double y1, double x2, double y2) {
        if (x1 == x2) {
            if (y2 > y1) {
                return 180;
            } else {
                return 0;
            }
        }
        double result = Math.atan((y2 - y1) / (x2 - x1)) / Math.PI * 180 + 90;
        if (x1 > x2) {
            result += 180;
        }
        return result;
    }

    public Region getCurrentRegion() {
        return currentRegion.get();
    }

    public SimpleObjectProperty<Region> currentRegionProperty() {
        return currentRegion;
    }

    public Vector<Region> getRegions() {
        return regions;
    }

    public Player getPlayer() {
        return player;
    }
}
