package cores.NPCEncounters;

import cores.characters.Player;
import cores.places.Region;
import cores.vehicles.Ship;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Pair;

import java.util.Random;

public class Bandit implements FightableNPC {
    private Player player;
    private IntegerProperty creditsDemanded;
    private Region dest;

    private static final int MAX_CREDITS_DEMANDED = 500;
    private static final int MIN_CREDITS_DEMANDED = 100;

    private static final int MAX_BANDIT_STRENGTH = 100; //how much health is lost if you lose

    public Bandit(Player player, Region dest) {

        this.player = player;
        this.creditsDemanded = new SimpleIntegerProperty(getRandomCredits());
        this.dest = dest;
    }

    @Override
    public Pair<Boolean, String> handleFight() {
        double fightSkillInfluence = player.calcInfluence(Player.SkillType.FIG);
        Random random = new Random();
        double winOrLoseNum = random.nextDouble();
        boolean win = winOrLoseNum > 0.5 * (1 - fightSkillInfluence);
        //This assumes that player had a 50/50 chance to begin with,
        // and then this number is reduced by whatever fightSkillInfluence ends up being

        if (win) {
            int credit = getRandomCredits();
            player.earn(credit);
            String message = "You won the battle and earned " + credit + " credit(s).";
            return new Pair<>(true, message);
        } else {
            player.loseAllCredits();
            int damage = (int) Math.round(Math.random() * MAX_BANDIT_STRENGTH);
            player.getShip().damage(damage);
            return new Pair<>(false, "Unfortunately, you lost the battle. The "
                    + "Bandit robbed all your credits and damaged your ship by "
                    + damage + " points.");
        }
    }

    @Override
    public Pair<Boolean, String> handleFlee() {
        double pilotSkillInfluence = player.calcInfluence(Player.SkillType.PIL);
        Random random = new Random();
        double fleeOrFailNum = random.nextDouble();
        boolean flee = fleeOrFailNum > 0.4 * (1 - pilotSkillInfluence);

        if (flee) {
            //decrement the fuel, but don't travel
            player.getShip().decrementFuel(player.getCurrentRegion(), dest, pilotSkillInfluence);
            return new Pair<>(true, "You successfully fled the Bandit!");
        } else {
            player.loseAllCredits();
            int damage =  (int) Math.round(Math.random() * MAX_BANDIT_STRENGTH);
            player.getShip().damage(damage);
            return new Pair<>(false, "Unfortunately, you failed and lost all your credits. "
                    + "Your ship also got damaged by " + damage + " point(s).");
        }
    }

    @Override
    public String handleForfeit() {
        Ship ship = player.getShip();

        if (player.getCredits() < this.creditsDemanded.get()) {
            if (ship.getItemInventory().isEmpty()) {
                int damage = (int) Math.round(Math.random() * MAX_BANDIT_STRENGTH);
                ship.damage(damage);
                return "You have neither money nor items! The Bandit got very "
                        + "angry and damaged your ship by " + damage + " point(s).";
            } else {
                ship.getItemInventory().clear();
                return "You don't have enough money! The Bandit "
                        + "got very angry and robbed all your items!";
            }
        } else {
            int credit = this.creditsDemanded.get();
            player.loseCredits(credit);
            return "You lost " + credit + " credit(s) to the Bandit.";
        }
    }

    private int getRandomCredits() {
        return (int) Math.round(Math.random() * (MAX_CREDITS_DEMANDED - MIN_CREDITS_DEMANDED)
                + MIN_CREDITS_DEMANDED);
    }

    @Override
    public String getDescription() {
        return "A Bandit intercepts you halfway and demands a toll of "
                + creditsDemanded.get() +  " credit(s).";
    }

    public void test() {

    }

    private void testForfeit() {

    }

    private void testFight() {

    }

    private void testFlee() {

    }
}
