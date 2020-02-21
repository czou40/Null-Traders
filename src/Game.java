import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.NoSuchElementException;

/*
   Handles most of the game logic and manages game state
 */
public class Game {
    private ObjectProperty<Difficulty> difficulty;
    private Player player;
    //private SimpleObjectProperty<Region> currentRegion;
    private Universe universe;

    public Game(Player player, Difficulty difficulty) {
        this.player = player;
        player.setGame(this);
        this.difficulty = new SimpleObjectProperty<>(difficulty);
    }

    public Game() {
        this.difficulty = new SimpleObjectProperty<>(Difficulty.CADET);
        this.player = new Player(this);
        this.universe = new Universe(player);
        player.setCurrentRegion(universe.getRandomRegion());
        //this.currentRegion = new SimpleObjectProperty<>();
        //setCurrentRegion(universe.getRandomRegion());
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

    /*
    public Region getCurrentRegion() {
        if (currentRegion == null) {
            throw new NoSuchElementException("Region has not been generated yet");
        } else {
            return currentRegion.get();
        }
    }

    public void setCurrentRegion(Region currentRegion) {
        this.currentRegion.set(currentRegion);
        if (!currentRegion.isFound()) {
            currentRegion.setFound(true);
        }
    }

    public SimpleObjectProperty<Region> currentRegionProperty() {
        return currentRegion;
    }
    */

    public Universe getUniverse() {
        if (universe == null) {
            throw new NoSuchElementException("Universe not created yet");
        } else {
            return universe;
        }
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }
}
