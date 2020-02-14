import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.NoSuchElementException;

/*
Handles most of the game logic and manages game state
 */
public class Game {
    private ObjectProperty<Difficulty> difficulty;
    private Player player;
    private UniverseMap universeMap;
    private Region currentRegion;

    public Game(Player player, Difficulty difficulty) {
        this.player = player;
        player.setGame(this);
        this.difficulty = new SimpleObjectProperty<>(difficulty);
    }

    public Game() {
        this.difficulty = new SimpleObjectProperty<>(Difficulty.CADET);
        this.player = new Player(this);
        this.universeMap = new UniverseMap(this);
        this.currentRegion = universeMap.getRandomRegion();
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

    public Region getCurrentRegion() {
        if (currentRegion == null) {
            throw new NoSuchElementException("Region has not been generated yet");
        } else {
            return currentRegion;
        }
    }

    public void setCurrentRegion(Region currentRegion) {
        currentRegion.setFound(true);
        this.currentRegion = currentRegion;
    }

    public UniverseMap getUniverseMap() {
        return universeMap;
    }
}