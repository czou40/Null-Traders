package cores.NPCEncounters;

import cores.Game;
import cores.characters.Player;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.stage.Stage;
import screens.BanditScreen;
import screens.EncounterScreen;

public class Bandit implements NPC, Fightable {
    private Player player;
    private IntegerProperty creditsDemanded;

    private static final int MAX_CREDITS_DEMANDED = 1000;

    public Bandit(Player player) {

        this.player = player;
        this.creditsDemanded =
                new SimpleIntegerProperty((int) Math.round(Math.random() * MAX_CREDITS_DEMANDED));
    }

    @Override
    public EncounterScreen getEncounterScreen(Game game, Stage primaryStage) {
        return new BanditScreen(primaryStage, game, this);
    }

    @Override
    public boolean handleFight(Player player) {
        return false;
    }

    @Override
    public boolean handleFlee(Player player) {
        return false;
    }

    @Override
    public void handleForfeit(Player player) {

    }
}
