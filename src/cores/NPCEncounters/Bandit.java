package cores.NPCEncounters;

import cores.characters.Player;
import cores.places.Region;
import cores.vehicles.Ship;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

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
    public boolean handleFight() {
        double fightSkillInfluence = player.calcInfluence(Player.SkillType.FIG);
        Random random = new Random();
        double winOrLoseNum = random.nextDouble();
        boolean win = winOrLoseNum > 0.5 * (1-fightSkillInfluence);
        //This assumes that player had a 50/50 chance to begin with,
        // and then this number is reduced by whatever fightSkillInfluence ends up being

        if(win){
            player.setCredits(player.getCredits()+ getRandomCredits());
            player.startTravelToRegion(dest, true);
            return true;
        } else {
            player.setCredits(0);
            player.getShip().damage(
                    (int) Math.round(Math.random() * MAX_BANDIT_STRENGTH)
            );
            return false;
        }
    }

    @Override
    public boolean handleFlee() {
        double pilotSkillInfluence = player.calcInfluence(Player.SkillType.PIL);
        Random random = new Random();
        double fleeOrFailNum = random.nextDouble();
        boolean flee = fleeOrFailNum > 0.4 * (1 - pilotSkillInfluence);

        if(flee) {
            //decrement the fuel, but don't travel
            player.getShip().decrementFuel(player.getCurrentRegion(), dest, pilotSkillInfluence);
            return true;
        } else {
            player.setCredits(0);
            player.getShip().damage(
                    (int) Math.round(Math.random() * MAX_BANDIT_STRENGTH)
            );
            return false;
        }

    }

    @Override
    public void handleForfeit() {
        Ship ship = player.getShip();

        if(player.getCredits() < this.creditsDemanded.get()){
            if(ship.getItemInventory().isEmpty()){
                ship.damage(
                        (int) Math.round(Math.random() * MAX_BANDIT_STRENGTH)
                );
            } else {
                ship.getItemInventory().clear();
            }
        } else {
            player.setCredits(player.getCredits() - this.creditsDemanded.get());
        }

    }

    private int getRandomCredits() {
        return (int) Math.round(Math.random() * (MAX_CREDITS_DEMANDED - MIN_CREDITS_DEMANDED)
                + MIN_CREDITS_DEMANDED);
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
