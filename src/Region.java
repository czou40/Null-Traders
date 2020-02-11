public class Region {
    private String name;
    private String description;
    private int technologyLevel;
    private int x;
    private int y;

    private NPC[] npcList;

    public Region(String name, String description, int technologyLevel, int x, int y) {
        this.name = name;
        this.description = description;
        this.technologyLevel = technologyLevel;
        this.x = x;
        this.y = y;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public NPC[] getNpcList() {
        return npcList;
    }
}