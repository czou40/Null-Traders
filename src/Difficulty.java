import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public enum Difficulty {
    CADET(200, 30), CAPTAIN(150, 20), ADMIRAL(100, 15);

    private Integer credits;
    private Integer startingSkillPoints;

    Difficulty(int credits, int startingSkillPoints) {
        this.credits = credits;
        this.startingSkillPoints = startingSkillPoints;
    }

    public Integer getCredits() {
        return credits;
    }

    public Integer getStartingSkillPoints() {
        return startingSkillPoints;
    }
}
