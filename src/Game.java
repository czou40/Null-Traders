/*
Handles most of the game logic and keeps reference to the player
 */
public class Game {
    private Difficulty difficulty;
    private Player player;

    public Game(Player player, Difficulty difficulty) {
        this.player = player;
        player.setGame(this);
        this.difficulty = difficulty;
    }

    public Game() {
        this(new Player(), Difficulty.CADET);   //defaults to easiest difficulty
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Player getPlayer() {
        return player;
    }
}
