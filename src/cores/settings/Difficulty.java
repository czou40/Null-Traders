package cores.settings;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public enum Difficulty {
    CADET(500,
    30,
   75,
   1000,
         500,
                    0.20
                    ),
    CAPTAIN(250,
    20,
    50,
    750,
        300,
0.40),
    ADMIRAL(150,
    15,
    25,
    500,
        100,
    0.60);

    private IntegerProperty credits;
    private IntegerProperty startingSkillPoints;
    private IntegerProperty startCargoCapacity;
    private IntegerProperty startFuelCapacity;
    private IntegerProperty startHealth;
    private DoubleProperty encounterChance;

    Difficulty(int credits, int startingSkillPoints,
               int startCargoCapacity, int startFuelCapacity, int startHealth,
               double encounterChance) {
        this.credits = new SimpleIntegerProperty(credits);
        this.startingSkillPoints = new SimpleIntegerProperty(startingSkillPoints);
        this.startCargoCapacity = new SimpleIntegerProperty(startCargoCapacity);
        this.startFuelCapacity = new SimpleIntegerProperty(startFuelCapacity);
        this.startHealth = new SimpleIntegerProperty(startHealth);
        this.encounterChance = new SimpleDoubleProperty(encounterChance);
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

    public double getEncounterChance() {
        return encounterChance.get();
    }

    public DoubleProperty encounterChanceProperty() {
        return encounterChance;
    }

    public void setEncounterChance(double encounterChance) {
        this.encounterChance.set(encounterChance);
    }
}
