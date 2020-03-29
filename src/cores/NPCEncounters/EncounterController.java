package cores.NPCEncounters;

import cores.Game;
import cores.NPCEncounters.screens.*;
import cores.characters.Player;
import cores.places.Region;
import javafx.stage.Stage;
import javafx.util.Pair;
import screens.*;
import uicomponents.UniverseMapController;

public class EncounterController {
    private Player player;
    private static Game game;
    private static Stage primaryStage;
    private static UniverseMapController mapController;
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
            handleResumeTravelToDest("Nothing happened during the journey.");
        }
    }

    public void handleResumeTravelToDest(String message) {
        player.resumeTravelAfterEncounter(dest);
        displayAfterEncounterScreen(message, true);
    }

    public void handleReturnToPlaceOfDeparture(String message) {
        displayAfterEncounterScreen(message, false);
    }

    public void handleGameOver() {
        Screen.closeMessageBox();
        new GameOverScreen(primaryStage, game).display();
    }

    public void displayEncounterOptionsScreen(NPC npc) {
        EncounterOptionScreen screen = new EncounterOptionScreen(primaryStage, game,
                npc, this);
        screen.displayAsMessageBox();
    }

    private void displayAfterEncounterScreen(String message, boolean willTravelToDestination) {
        AfterEncounterScreen screen =
                new AfterEncounterScreen(primaryStage, game, this);
        if (willTravelToDestination) {
            screen.setDestination(dest.getName());
        }
        if (player.isDead()) {
            screen.setGameOver(true);
        }
        screen.setMessage(message);
        screen.displayAsMessageBox();
    }

    public void displayFightScreen(FightableNPC npc) {
        new FightScreen(primaryStage, game, npc, this).displayAsMessageBox();
    }

    public void displayTradeScreen(TradableNPC npc) {
        new TradeScreen(primaryStage, game, npc, this).displayAsMessageBox();
    }

    public void handleBuyEvent(TradeScreen screen, TradableNPC npc) {
        try {
            npc.handleBuy();
            screen.update();
            screen.displayTransactionComplete();
        } catch (Exception e) {
            screen.displayTransactionFailed(e.getMessage());
        }
    }

    public void handleNegotiateEvent(TradeScreen screen, TradableNPC npc) {
        boolean result = npc.handleNegotiate();
        screen.update();
        if (result) {
            screen.displayNegotiationSuccessful();
        } else {
            screen.displayNegotiationFailed();
        }
    }

    public void handleFightEvent(FightScreen screen, FightableNPC npc) {
        Pair<Boolean, String> result = npc.handleFight();
        screen.update(result.getValue());
    }

    public void handleFleeEvent(FightableNPC npc) {
        Pair<Boolean, String> result = npc.handleFlee();
        handleReturnToPlaceOfDeparture(result.getValue());
    }

    public void handleForfeitEvent(FightableNPC npc) {
        String result = npc.handleForfeit();
        handleResumeTravelToDest(result);
    }

    public void handleRobEvent(RobbableNPC npc) {
        Pair<Boolean, String> result = npc.handleRob();
        handleResumeTravelToDest(result.getValue());
    }

    public static void setupScreenEnvironment(Game game, Stage primaryStage) {
        EncounterController.game = game;
        EncounterController.primaryStage = primaryStage;
    }

    public static void setMapController(UniverseMapController controller) {
        EncounterController.mapController = controller;
    }


    public void goBackToMapScreen() {
        new MapScreen(primaryStage, game).display();
    }

    public void notifyMapController() {
        mapController.handleTravelAfterEncounterFinished(dest);
    }
}
