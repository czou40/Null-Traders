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
    private SimpleObjectProperty<Region> currentRegion;
    public static final int WIDTH = 2000;
    public static final int HEIGHT = 2000;
    private static final int REGION_NUMBER = 30;

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
    }

    public Region getRandomRegion() {
        return regions.get((int) (Math.random() * regions.size()));
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
