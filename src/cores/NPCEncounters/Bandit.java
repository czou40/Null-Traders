package cores.NPCEncounters;

import cores.Game;
import cores.characters.Player;
import cores.vehicles.Ship;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.stage.Stage;
import screens.BanditScreen;
import screens.EncounterScreen;

import java.util.Random;

public class Bandit implements NPC, Fightable {
    private Player player;
    private IntegerProperty creditsDemanded;

    private static final int MAX_CREDITS_DEMANDED = 500;
    private static final int MIN_CREDITS_DEMANDED = 100;

    public Bandit(Player player) {

        this.player = player;
        this.creditsDemanded =
                new SimpleIntegerProperty(
                (int) Math.round(Math.random() * (MAX_CREDITS_DEMANDED - MIN_CREDITS_DEMANDED)
                        + MIN_CREDITS_DEMANDED)
                );
    }

    @Override
    public EncounterScreen getEncounterScreen(Game game, Stage primaryStage) {
        return new BanditScreen(primaryStage, game, this);
    }

    @Override
    public boolean handleFight(Player player) {
        double fightSkillInfluence = player.calcInfluence(Player.SkillType.FIG);
        Random random = new Random();
        double winOrLoseNum = random.nextDouble();
        boolean winOrLose = winOrLoseNum > 0.5 * (1-fightSkillInfluence);
        //This assumes that player had a 50/50 chance to begin with,
        // and then this number is reduced by whatever fightSkillInfluence ends up being

        if(winOrLose){
            player.setCredits(player.getCredits()+50); //Arbitrarily chose 50 as player's reward
            return true;
        } else {
            player.setCredits(0);
            player.getShip().setHealth(player.getShip().getHealth() - 100); //Arbitrarily chose 100
            return false;
        }

    }

    @Override
    public boolean handleFlee(Player player) {
        double pilotSkillInfluence = player.calcInfluence(Player.SkillType.PIL);
        Random random = new Random();
        double fleeOrFailNum = random.nextDouble();
        boolean fleeOrFail = fleeOrFailNum > 0.6 * (1 - pilotSkillInfluence);

        if(fleeOrFail){
            return true;
        } else {
            player.setCredits(0);
            player.getShip().setHealth(player.getShip().getHealth() - 100);
            return false;
        }

    }

    @Override
    public void handleForfeit(Player player) {
        Ship ship = player.getShip();

        if(player.getCredits() < this.creditsDemanded.getValue()){
            if(ship.getItemInventory().isEmpty()){
                ship.setHealth(player.getShip().getHealth() - 100);
            } else {
                ship.getItemInventory().clear();
            }
        } else {
            player.setCredits(player.getCredits() - this.creditsDemanded.getValue());
        }

    }
}
