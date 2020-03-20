package cores.NPCEncounters;

import cores.Game;
import cores.GameOverException;
import cores.NPCEncounters.Screens.*;
import cores.characters.Player;
import cores.places.Region;
import javafx.stage.Stage;
import screens.*;

public class EncounterController {
    private Player player;
    private static Game game;
    private static Stage primaryStage;
    private Region dest;

    public EncounterController(Player player) {
        this.player = player;
    }



    public void handleEncounter(Region dest) {
        NPC npc = EncounterFactory.generateRandomEncounter(player, game.getDifficulty(), dest);
        if (game == null || primaryStage == null) {
            throw new IllegalStateException("You have not yet set up the screen environment!");
        }
        this.dest = dest;
        if (npc != null) {
            displayEncounterOptionsScreen(npc);
        } else {
            handleResumeTravel("Nothing happened during the journey.");
        }
    }

    public void handleResumeTravel(String message) {
        try {
            player.resumeTravelAfterEncounter(dest);
            displayWillArriveScreen(message);
        } catch (GameOverException e) {
            new GameOverScreen(primaryStage, game).display();
        }
    }

    public void displayEncounterOptionsScreen(NPC npc) {
        EncounterOptionScreen screen = new EncounterOptionScreen(primaryStage, game, npc, this);
        screen.display();
    }

    private void displayWillArriveScreen(String message) {
        WillArriveScreen screen =
                new WillArriveScreen(primaryStage, game, this);
        screen.setDestination(dest.getName());
        screen.setMessage(message);
        screen.display();
    }

    public void displayFightScreen(FightableNPC npc) {
        new FightScreen(primaryStage, game, npc, this).display();
    }

    public void displayTradeScreen(TradableNPC npc) {
        new TradeScreen(primaryStage, game, npc, this).display();
    }

    public void displayRobScreen(RobbableNPC npc) {
        new RobScreen(primaryStage, game, npc, this).display();
    }

    public void handleBuyEvent(TradeScreen screen, TradableNPC npc) {
        try {
            npc.handleBuy();
            screen.updateTradeScreen();
            screen.displayTransactionComplete();
        } catch (Exception e) {
            screen.displayTransactionFailed(e.getMessage());
        }
    }

    public void handleNegotiateEvent(TradeScreen screen, TradableNPC npc) {
        boolean result = npc.handleNegotiate();
        screen.updateTradeScreen();
        if (result) {
            screen.displayNegotiationSuccessful();
        } else {
            screen.displayNegotiationFailed();
        }
    }

    public static void setupScreenEnvironment(Game game, Stage primaryStage) {
        EncounterController.game = game;
        EncounterController.primaryStage = primaryStage;
    }


    public void goBackToMapScreen() {
        new MapScreen(primaryStage, game).display();
    }
}
