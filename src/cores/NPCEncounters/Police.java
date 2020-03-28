package cores.NPCEncounters;

import cores.characters.Player;
import cores.objects.Item;
import cores.places.Region;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Pair;

import java.util.Random;

public class Police implements FightableNPC {
    private Player player;
    private Item confiscatedItem;
    private Region dest;
    private IntegerProperty evasionFine;

    private static final int MAX_POLICE_STRENGTH = 100; //how much health is lost if you lose
    private static final int MAX_FINE = 100;
    private static final int MIN_FINE = 50;


    public Police(Player player, Item confiscatedItem, Region dest) {
        this.player = player;
        this.confiscatedItem = confiscatedItem;
        this.dest = dest;
        this.evasionFine = new SimpleIntegerProperty(getRandomCredits());
    }

//    @Override
//    public EncounterOptionScreen getEncounterScreen(Game game, Stage primaryStage) {
//        return new PoliceScreen(primaryStage, game, this);
//    }

    @Override
    public Pair<Boolean, String> handleFight() {
        double fightSkillInfluence = player.calcInfluence(Player.SkillType.FIG);
        Random random = new Random();
        double winOrLoseNum = random.nextDouble();
        boolean win = winOrLoseNum > 0.5 * (1-fightSkillInfluence);
        //This assumes that player had a 50/50 chance to begin with,
        // and then this number is reduced by whatever fightSkillInfluence ends up being

        if(win){
            return new Pair<>(true, "You fought off the Police!");
        } else {
            player.getShip().getItemInventory().remove(confiscatedItem);
            int damage =  (int) Math.round(Math.random() * MAX_POLICE_STRENGTH);
            player.getShip().damage(damage);
            int fine = getEvasionFine();
            player.loseCredits(fine);
            return new Pair<>(false, String.format(
                    "You lost the battle! Your ship was damaged by %d point(s). "
                            + "You had to confiscate all your %s(s) and "
                            + "pay a fine of %d credit(s).",
                    damage, confiscatedItem.getName(), fine));
        }
    }

    @Override
    public Pair<Boolean, String> handleFlee() {

        double pilotSkillInfluence = player.calcInfluence(Player.SkillType.PIL);
        Random random = new Random();
        double fleeOrFailNum = random.nextDouble();
        boolean flee = fleeOrFailNum > 0.4 * (1 - pilotSkillInfluence);

        if (flee) {
            player.getShip().decrementFuel(player.getCurrentRegion(), dest, pilotSkillInfluence);
            return new Pair<>(true, "You fled to your place of origin!");
        } else {
            player.getShip().getItemInventory().remove(confiscatedItem);
            int damage = (int) Math.round(Math.random() * MAX_POLICE_STRENGTH);
            player.getShip().damage(damage);
            int fine = getEvasionFine();
            player.loseCredits(fine);
            player.getShip().decrementFuel(player.getCurrentRegion(), dest, pilotSkillInfluence);
            return new Pair<>(false, "You failed to flee. Your ship got damaged by "
                    + damage + " point(s). You had to confiscate "
                    + "all your " + confiscatedItem.getName() + "(s) and pay a fine of "
                    + fine + " credit(s).");
        }
    }

    @Override
    public String handleForfeit() {
        player.getShip().getItemInventory().remove(confiscatedItem);
        return "The Police confiscated all your " + confiscatedItem.getName() + "(s).";
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

    @Override
    public String getDescription() {
        return "The Police suspects that you are conducting illegal transactions "
                + "and wants to search your ship for " + confiscatedItem.getName() + "(s)";
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
