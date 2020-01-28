public enum Difficulty {
    CADET(200), CAPTAIN(150), ADMIRAL(100);

    private int credits;

    private Difficulty(int credits) {
        this.credits = credits;
    }

    public int getCredits() {
        return credits;
    }
}
