public enum RegionData {
    GEORGIAN_TECH("Georgian Tech", "A place for the CSest of CS bois", 10)
    , USICGA("U(sic)GA", "A frontier planet on the verge of extinction. " +
            "                               It's inhabitants are known for their primitive ways.",
                            1)
    , CWORLD("C World", "A low level planet filled with pointers.", 3)
    , AUHSAST("Auhsast", "A cold, sparse planet with secrets lurking beneath the surface.", 6)
    , SHIULVZ("Shiulvz", "A dangerous planet selling questionable items. Is it worth the price?", 8)
    , ATHREI("Athrei", "A booming planet of infinite sunlight.", 9)
    , FLEU("Fleu", "A planet with a vast skyline and infinite clouds", 7)
    , KROARTHAN("Kroarthan", "", 6)
    , KRANTLU("Krantlu", "", 5)
    , PROARVIAVZO("Proarviavzo", "The magnificent", 8)
    ;

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
