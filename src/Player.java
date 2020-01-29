import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class Player {
    private Game game;

    private StringProperty name;
    private IntegerProperty pilot;
    private IntegerProperty fighter;
    private IntegerProperty merchant;
    private IntegerProperty engineer;
    private IntegerProperty credits;

    public Player(Game game, String name, Integer pilot, Integer fighter, Integer merchant, Integer engineer, Integer credits) {
        this.game = game;
        this.name = new SimpleStringProperty(name);
        this.pilot = new SimpleIntegerProperty(pilot);
        this.fighter = new SimpleIntegerProperty(fighter);
        this.merchant = new SimpleIntegerProperty(merchant);
        this.engineer = new SimpleIntegerProperty(engineer);
        this.credits = new SimpleIntegerProperty(credits);
    }

    public Player(Game game, String name) {
        this(game, name, 0, 0, 0, 0, game.getDifficulty().getCredits());
    }

    public Player(Game game) {
        this(game, "", 0, 0, 0, 0, game.getDifficulty().getCredits());
    }

    public Player() {
        this(new Game());
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public IntegerProperty pilotProperty() {
        return pilot;
    }

    public Integer getPilot() {
        return pilot.getValue();
    }

    public void setPilot(Integer pilot) {
        this.pilot = new SimpleIntegerProperty(pilot);
    }

    public IntegerProperty fighterProperty() {
        return fighter;
    }

    public Integer getFighter() {
        return fighter.getValue();
    }

    public void setFighter(Integer fighter) {
        this.fighter = new SimpleIntegerProperty(fighter);
    }

    public IntegerProperty merchantProperty() {
        return merchant;
    }

    public Integer getMerchant() {
        return merchant.getValue();
    }

    public void setMerchant(Integer merchant) {
        this.merchant = new SimpleIntegerProperty(merchant);
    }

    public IntegerProperty engineerProperty() {
        return engineer;
    }

    public Integer getEngineer() {
        return engineer.getValue();
    }

    public void setEngineer(Integer engineer) {
        this.engineer = new SimpleIntegerProperty(engineer);
    }

    public IntegerProperty creditsProperty() {
        return credits;
    }

    public Integer getCredits() {
        return credits.getValue();
    }

    public void setCredits(Integer credits) {
        this.credits = new SimpleIntegerProperty(credits);
    }

    public Integer sumOfPoints() {
        return getPilot() + getFighter() + getMerchant() + getEngineer();
    }
}
