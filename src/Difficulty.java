import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public enum Difficulty {
    CADET(200, 30), CAPTAIN(150, 20), ADMIRAL(100, 15);

    private IntegerProperty credits;
    private IntegerProperty startingSkillPoints;

    Difficulty(int credits, int startingSkillPoints) {
        this.credits = new SimpleIntegerProperty(credits);
        this.startingSkillPoints = new SimpleIntegerProperty(startingSkillPoints);
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
}
