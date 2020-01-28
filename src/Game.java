public class Game {
    private Difficulty difficulty;

    public Game(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Game() {
        this(Difficulty.ADMIRAL);   //defaults to hardest difficulty, because we're pro gamers
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
