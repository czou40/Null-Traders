import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Region {
    private String name;
    private String description;
    private int technologyLevel;
    private double x;
    private double y;
    private BooleanProperty found;  //if the player has found the region

    private NPC[] npcList;

    public Region(String name, String description, int technologyLevel,
                  int x, int y, boolean found) {

        this.name = name;
        this.description = description;
        this.technologyLevel = technologyLevel;
        this.x = x;
        this.y = y;
        this.found = new SimpleBooleanProperty(found);
        this.npcList = new NPC[5];
    }

    public Region(RegionData data, int x, int y, boolean found) {
        this(data.getName(), data.getDescription(), data.getTechnologyLevel(), x, y, found);
    }

    public Region(RegionData data, int x, int y) {
        this(data, x, y, false);
    }

    public Region(RegionData data) {
        this(data, 0, 0);
    }


    //NOTE: Only add setters if necessary

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getTechnologyLevel() {
        return technologyLevel;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isFound() {
        return found.getValue();
    }

    public void setFound(boolean found) {
        this.found.setValue(found);
    }

    public BooleanProperty foundProperty() {
        return found;
    }

    public NPC[] getNpcList() {
        return npcList;
    }
}