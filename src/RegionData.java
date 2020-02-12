public enum RegionData {
    GEORGIAN_TECH("Georgian Tech", "A place for CS bois", 10);

    private String name;
    private String description;
    private int technologyLevel;

    RegionData(String name, String description, int technologyLevel) {
        this.name = name;
        this.description = description;
        this.technologyLevel = technologyLevel;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getTechnologyLevel() {
        return technologyLevel;
    }
}
