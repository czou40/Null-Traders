public class Region {
    private String name;
    private String description;
    private int technologyLevel;
    private int[] coordinates;

    private NPC[] npcList;

    public Region(String name, String description, int technologyLevel, int[] coordinates) {
        this.name = name;
        this.description = description;
        this.technologyLevel = technologyLevel;
        this.coordinates = coordinates;
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

    public int[] getCoordinates() {
        return coordinates;
    }

    public NPC[] getNpcList() {
        return npcList;
    }
}