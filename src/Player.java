import java.util.Random;

public class Player {
    private Game game;

    private String name;
    private Integer pilot;
    private Integer fighter;
    private Integer merchant;
    private Integer engineer;
    private Integer credits;

    public Player(Game game, String name, Integer pilot, Integer fighter, Integer merchant, Integer engineer, Integer credits) {
        this.game = game;
        this.name = name;
        this.pilot = pilot;
        this.fighter = fighter;
        this.merchant = merchant;
        this.engineer = engineer;
        this.credits = credits;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPilot() {
        return pilot;
    }

    public void setPilot(Integer pilot) {
        this.pilot = pilot;
    }

    public Integer getFighter() {
        return fighter;
    }

    public void setFighter(Integer fighter) {
        this.fighter = fighter;
    }

    public Integer getMerchant() {
        return merchant;
    }

    public void setMerchant(Integer merchant) {
        this.merchant = merchant;
    }

    public Integer getEngineer() {
        return engineer;
    }

    public void setEngineer(Integer engineer) {
        this.engineer = engineer;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }
}
