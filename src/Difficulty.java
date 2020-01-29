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

    public IntegerProperty creditsProperty() {
        return credits;
    }

    public int getCredits() {
        return credits.getValue();
    }

    public IntegerProperty startingSkillPointsProperty() {
        return startingSkillPoints;
    }

    public int getStartingSkillPoints() {
        return startingSkillPoints.getValue();
    }
}
