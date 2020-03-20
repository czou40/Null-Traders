package cores;

public class GameOverException extends Exception {
    public GameOverException() {
        super("You lost the battle! Game over!");
    }
}
