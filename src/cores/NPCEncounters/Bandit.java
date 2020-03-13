package cores.NPCEncounters;

import cores.Game;
import cores.characters.Player;
import javafx.stage.Stage;
import screens.BanditScreen;
import screens.EncounterScreen;
import screens.Screen;

public class Bandit implements NPC, Fightable {
    private Player player;

    public Bandit(Player player) {
        this.player = player;
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
    public boolean handleForfeit(Player player) {
        return false;
    }
}
