import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/*
Handles most of the game logic and keeps reference to the player
 */
public class Game {
    private ObjectProperty<Difficulty> difficulty;
    private Player player;

    public Game(Player player, Difficulty difficulty) {
        this.player = player;
        player.setGame(this);
        this.difficulty = new SimpleObjectProperty<>(difficulty);
    }

    public Game() {
        this.difficulty = new SimpleObjectProperty<>(Difficulty.CADET);
        this.player = new Player(this);
    }

    public Difficulty getDifficulty() {
        return difficulty.getValue();
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty.setValue(difficulty);
    }

    public ObjectProperty<Difficulty> difficultyProperty() {
        return difficulty;
    }

    public Player getPlayer() {
        return player;
    }
}