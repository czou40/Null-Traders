import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Region {
    private String name;
    private String description;
    private int technologyLevel;
    private double x;
    private double y;
    private BooleanProperty found;  //if the player has found the region
    private BooleanProperty isCurrentRegion;
    private Marketplace marketplace;    //new field
    private NPC[] npcList;

    public Region(String name, String description, int technologyLevel,
                  int x, int y) {

        this.name = name;
        this.description = description;
        this.technologyLevel = technologyLevel;
        this.x = x;
        this.y = y;
        this.found = new SimpleBooleanProperty(false);
        this.isCurrentRegion = new SimpleBooleanProperty(false);
        this.marketplace = new Marketplace();
        this.npcList = new NPC[5];
    }

    public Region(RegionData data, int x, int y) {
        this(data.getName(), data.getDescription(), data.getTechnologyLevel(), x, y);
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

    public boolean isCurrentRegion() {
        return isCurrentRegion.get();
    }

    public BooleanProperty isCurrentRegionProperty() {
        return isCurrentRegion;
    }

    public void setIsCurrentRegion(boolean isCurrentRegion) {
        this.isCurrentRegion.set(isCurrentRegion);
    }

    public NPC[] getNpcList() {
        return npcList;
    }

    /*
    Calculate distance to another region.
     */
    public double distanceTo(Region other) {
        double diffX = getX() - other.getX();
        double diffY = getY() - other.getY();
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }


}