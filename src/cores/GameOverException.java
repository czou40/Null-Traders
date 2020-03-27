package cores;

public class GameOverException extends Exception {
    public GameOverException() {
        super("Your ship is completely destroyed! Game over!");
    }
}
