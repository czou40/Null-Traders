package cores;

import cores.characters.Player;
import cores.places.Universe;
import cores.settings.Difficulty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.NoSuchElementException;

/*
   Handles most of the game logic and manages game state
 */
public class Game {
    private ObjectProperty<Difficulty> difficulty;
    private Player player;
    private Universe universe;

    public Game() {
        this.difficulty = new SimpleObjectProperty<>(Difficulty.CADET);
        this.player = new Player(this);
        this.universe = new Universe(player);
        player.setCurrentRegion(universe.getRandomRegion());
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

    public Universe getUniverse() {
        if (universe == null) {
            throw new NoSuchElementException("Universe not created yet");
        } else {
            return universe;
        }
    }
}
