package cores.NPCEncounters;

import cores.Game;
import cores.characters.Player;
import cores.places.Region;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

//THIS CLASS IS FOR TESTING PURPOSES ONLY
public class EncounterTests {
    private Game game;
    private Player player;
    private Region dest;

    private static final int TIMEOUT = 200;

    @Before
    public void setup() {
        game = new Game();
        player = game.getPlayer();
        Vector<Region> regions = game.getUniverse().getRegions();
        for (Region r : regions) {
            if (r.getName().equals("Georgian Tech")) {
                player.setCurrentRegion(r);
            }

            if (r.getName().equals("Proarviavzo")) {
                dest = r;
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void testBanditForfeit() {
        Bandit bandit = new Bandit(player, dest);
        bandit.handleForfeit();
    }

    //Other tests that are not J-Units
    public static void testBandit() {

    }
}
