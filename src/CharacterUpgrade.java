public enum CharacterUpgrade {
    PILOT("Pilot Enhancer", 5, 0, 0, 0),
    FIGHTER("Enhancer", 0, 5, 0, 0),
    MERCHANT("Enhancer", 0, 0, 5, 0),
    ENGINEER("Enhancer", 0, 0, 0, 5);


    private String name;
    private int pilot;
    private int fighter;
    private int merchant;
    private int engineer;

    CharacterUpgrade(String name, int pilot, int fighter, int merchant, int engineer) {
        this.name = name;
        this.pilot = pilot;
        this.fighter = fighter;
        this.merchant = merchant;
        this.engineer = engineer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPilot() {
        return pilot;
    }

    public void setPilot(int pilot) {
        this.pilot = pilot;
    }

    public int getFighter() {
        return fighter;
    }

    public void setFighter(int fighter) {
        this.fighter = fighter;
    }

    public int getMerchant() {
        return merchant;
    }

    public void setMerchant(int merchant) {
        this.merchant = merchant;
    }

    public int getEngineer() {
        return engineer;
    }

    public void setEngineer(int engineer) {
        this.engineer = engineer;
    }
}
