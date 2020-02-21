import javafx.beans.property.SimpleObjectProperty;
import java.util.Vector;

/**
 * This class represents the space in which the game take place.
 */
public class Universe {
    private Player player;
    private Vector<Region> regions;
    private SimpleObjectProperty<Region> currentRegion;
    static final int WIDTH = 1920;
    static final int HEIGHT = 1080;

    public Universe(Player player) {
        this.player = player;
        regions = new Vector<>();
        generateRegions();
        currentRegion = new SimpleObjectProperty<>();
        currentRegion.bind(player.currentRegionProperty());
    }

    private void generateRegions() {
        Vector<Integer> spotsLeft = new Vector<>();
        for (int i = 0; i < RegionData.values().length; i++) {
            spotsLeft.add(i);
        }

        RegionData[] regionData = RegionData.values();
        final int numRows = 2;
        for (RegionData r : regionData) {
            int spot = spotsLeft.remove((int) (Math.random() * spotsLeft.size()));
            int spotX = spot % (regionData.length / numRows); //0 to number of regions / 2
            int spotY = spot / (regionData.length / numRows);   //0 or 1
            int spotWidth = WIDTH / (regionData.length / numRows);
            int spotHeight = HEIGHT / numRows;
            //regions must be a certain dist from the borders.
            int x = (int) ((spotX + Math.random() * 0.8 + 0.1) * spotWidth);
            int y = (int) ((spotY + Math.random() * 0.8 + 0.1) * spotHeight);
            regions.add(new Region(r, x, y));
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
