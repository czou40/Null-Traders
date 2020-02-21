public enum RegionData {
    GEORGIAN_TECH("Georgian Tech", "A place for the CSest \nof CS bois", 10),
    USICGA("uga", "A frontier planet on the verge of extinction. "
                                    + "\nIt's inhabitants are known for their primitive ways.",
             1),
    CWORLD("C World", "A low level planet \nfilled with pointers.", 3),
    AUHSAST("Auhsast", "A cold, sparse planet with secrets "
                                        + "\nlurking beneath the surface.", 6),
    SHIULVZ("Shiulvz", "A dangerous planet selling questionable items. "
                                        + "\nIs it worth the price?", 8),
    ATHREI("Athrei", "A booming planet of infinite sunlight.", 9),
    FLEU("Fleu", "A planet with a vast \nskyline and infinite clouds", 7),
    KROARTHAN("Kroarthan", "A planet of giant proportions.", 6),
    KRANTLU("Krantlu", "A young, developing planet, \nif only...", 4),
    PROARVIAVZO("Proarviavzo", "A magnificent planet of vast wealth.", 8);

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
