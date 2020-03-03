package cores.settings;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public enum Difficulty {
    CADET(200, 30, 75, 1000, 500),
    CAPTAIN(150, 20, 50, 500, 300),
    ADMIRAL(100, 15, 25, 400, 100);

    private IntegerProperty credits;
    private IntegerProperty startingSkillPoints;
    private IntegerProperty startCargoCapacity;
    private IntegerProperty startFuelCapacity;
    private IntegerProperty startHealth;

    Difficulty(int credits, int startingSkillPoints,
               int startCargoCapacity, int startFuelCapacity, int startHealth) {
        this.credits = new SimpleIntegerProperty(credits);
        this.startingSkillPoints = new SimpleIntegerProperty(startingSkillPoints);
        this.startCargoCapacity = new SimpleIntegerProperty(startCargoCapacity);
        this.startFuelCapacity = new SimpleIntegerProperty(startFuelCapacity);
        this.startHealth = new SimpleIntegerProperty(startHealth);
    }

    public Integer getCredits() {
        return credits.getValue();
    }

    public Integer getStartingSkillPoints() {
        return startingSkillPoints.getValue();
    }

    public IntegerProperty creditProperty() {
        return credits;
    }

    public IntegerProperty startingSkillPointsProperty() {
        return startingSkillPoints;
    }

    public int getStartCargoCapacity() {
        return startCargoCapacity.get();
    }

    public IntegerProperty startCargoCapacityProperty() {
        return startCargoCapacity;
    }

    public void setStartCargoCapacity(int startCargoCapacity) {
        this.startCargoCapacity.set(startCargoCapacity);
    }

    public int getStartFuelCapacity() {
        return startFuelCapacity.get();
    }

    public IntegerProperty startFuelCapacityProperty() {
        return startFuelCapacity;
    }

    public void setStartFuelCapacity(int startFuelCapacity) {
        this.startFuelCapacity.set(startFuelCapacity);
    }

    public int getStartHealth() {
        return startHealth.get();
    }

    public IntegerProperty startHealthProperty() {
        return startHealth;
    }

    public void setStartHealth(int startHealth) {
        this.startHealth.set(startHealth);
    }
}
