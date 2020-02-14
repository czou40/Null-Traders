import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.NoSuchElementException;

/*
Handles most of the game logic and manages game state
 */
public class Game {
    private ObjectProperty<Difficulty> difficulty;
    private Player player;
    private Region currentRegion;
    private UniverseMap universe;

    public Game(Player player, Difficulty difficulty) {
        this.player = player;
        player.setGame(this);
        this.difficulty = new SimpleObjectProperty<>(difficulty);
    }

    public Game() {
        this.difficulty = new SimpleObjectProperty<>(Difficulty.CADET);
        this.player = new Player(this);
    }

    public void travelToRegion(Region dest) {
        setCurrentRegion(dest);
        universe.updateDots();

        if (universe.getRegionDescriptions() != null) {
            for (RegionDescriptionBox box : universe.getRegionDescriptions()) {
                if (box.getRegion().equals(currentRegion)) {
                    box.setLabelsFull();
                } else {
                    box.setLabelsOnlyName();
                }
            }
        }
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
        this.currentRegion = currentRegion;
        if (!currentRegion.isFound()) {
            currentRegion.setFound(true);
        }
    }

    public UniverseMap getUniverse() {
        if (universe == null) {
            throw new NoSuchElementException("Universe not created yet");
        } else {
            return universe;
        }
    }

    public void setUniverse(UniverseMap universe) {
        this.universe = universe;
    }
}