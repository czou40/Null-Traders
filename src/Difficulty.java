public enum Difficulty {
    CADET(200, 30), CAPTAIN(150, 20), ADMIRAL(100, 15);

    private int credits;
    private int startingSkillPoints;

    Difficulty(int credits, int startingSkillPoints) {
        this.credits = credits;
        this.startingSkillPoints = startingSkillPoints;
    }

    public int getCredits() {
        return credits;
    }

    public int getStartingSkillPoints() {
        return startingSkillPoints;
    }
}
