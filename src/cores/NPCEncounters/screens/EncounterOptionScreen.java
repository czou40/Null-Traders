package cores.NPCEncounters.screens;

import cores.Game;
import cores.NPCEncounters.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import screens.Screen;
import uicomponents.MyGridPane;

public class EncounterOptionScreen extends Screen {
    private NPC npc;
    private Button fightButton;
    private Button fleeButton;
    private Button tradeButton;
    private Button ignoreButton;
    private Button robButton;
    private Button forfeitButton;
    private ImageView npcDisplay;
    private Label infoLabel;
    private EncounterController controller;

    public EncounterOptionScreen(Stage primaryStage, Game game, NPC npc, EncounterController controller) {
        super(primaryStage, game);
        int count = 0;
        this.npc = npc;
        this.controller = controller;
    }

    @Override
    public Pane constructRoot() {
        VBox root = new VBox(20);
        HBox buttonsPane = new HBox(20);
        if (npc instanceof FightableNPC) {
            this.fightButton = new Button("Fight");
            fightButton.setOnAction(event -> {
                controller.displayFightScreen((FightableNPC) npc);
            });
            this.fleeButton = new Button("Flee");
            fleeButton.setOnAction(event -> {
                controller.handleFleeEvent((FightableNPC) npc);
            });
            this.forfeitButton = new Button("Forfeit");
            forfeitButton.setOnAction(event -> {
                controller.handleForfeitEvent((FightableNPC) npc);
            });
            buttonsPane.getChildren().addAll(fightButton, fleeButton, forfeitButton);
        }
        if (npc instanceof TradableNPC) {
            this.tradeButton = new Button("Trade");
            tradeButton.setOnAction(event -> {
                controller.displayTradeScreen((TradableNPC) npc);
            });
            buttonsPane.getChildren().addAll(tradeButton);
        }
        if (npc instanceof RobbableNPC) {
            this.robButton = new Button("Rob");
            robButton.setOnAction(event -> {
                controller.handleRobEvent((RobbableNPC) npc);
            });
            buttonsPane.getChildren().addAll(robButton);
        }

        if (npc instanceof IgnorableNPC) {
            this.ignoreButton = new Button("Ignore");
            ignoreButton.setOnAction(e -> {
                controller.handleResumeTravelToDest("Nothing Happened in the journey.");
            });
            buttonsPane.getChildren().addAll(ignoreButton);
        }
        infoLabel = new Label(npc.getDescription() + " What would you like to do?");
        this.npcDisplay = new ImageView();
        root.getChildren().addAll(infoLabel, buttonsPane);
        return root;
    }
}
