package cores.NPCEncounters;

import cores.Game;
import cores.characters.Player;
import cores.objects.Item;
import cores.places.Region;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.stage.Stage;
import screens.EncounterScreen;
import screens.PoliceScreen;

import java.util.Random;

public class Police implements NPC, Fightable {
    private Player player;
    private Item confiscatedItem;
    private Region dest;
    private IntegerProperty evasionFine;

    private static final int MAX_POLICE_STRENGTH = 100; //how much health is lost if you lose
    private static final int MAX_FINE = 300;
    private static final int MIN_FINE = 100;

    public Police(Player player, Item confiscatedItem, Region dest) {
        this.player = player;
        this.confiscatedItem = confiscatedItem;
        this.dest = dest;
        this.evasionFine = new SimpleIntegerProperty(getRandomCredits());
    }

    @Override
    public EncounterScreen getEncounterScreen(Game game, Stage primaryStage) {
        return new PoliceScreen(primaryStage, game, this);
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
            player.travelToRegion(dest, true);
            return true;
        } else {
            player.getShip().getItemInventory().remove(confiscatedItem);
            player.getShip().damage(
                    (int) Math.round(Math.random() * MAX_POLICE_STRENGTH)
            );
            player.setCredits(Math.max(player.getCredits() - getEvasionFine(), 0));
            player.travelToRegion(dest, true);
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
            player.getShip().getItemInventory().remove(confiscatedItem);
            player.getShip().damage(
                    (int) Math.round(Math.random() * MAX_POLICE_STRENGTH)
            );
            player.setCredits(Math.max(player.getCredits() - getEvasionFine(), 0));
            player.getShip().decrementFuel(player.getCurrentRegion(), dest, pilotSkillInfluence);
            return false;
        }
    }

    @Override
    public void handleForfeit() {
        player.getShip().getItemInventory().remove(confiscatedItem);
        player.travelToRegion(dest, true);
    }

    public int getEvasionFine() {
        return evasionFine.get();
    }

    public IntegerProperty evasionFineProperty() {
        return evasionFine;
    }

    private int getRandomCredits() {
        return (int) Math.round(Math.random() * (MAX_FINE - MIN_FINE)
                + MIN_FINE);
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
