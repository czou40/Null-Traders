package cores.NPCEncounters.screens;

import cores.Game;
import cores.NPCEncounters.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
        VBox root = new VBox();
        MyGridPane myGridPane = new MyGridPane(null, MyGridPane.getSpan(6));
        int count = 0;
        if (npc instanceof FightableNPC) {
            count += 2;
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
            myGridPane.addRow(0, fightButton, fleeButton, forfeitButton);
        }
        if (npc instanceof TradableNPC) {
            count++;
            this.tradeButton = new Button("Trade");
            tradeButton.setOnAction(event -> {
                controller.displayTradeScreen((TradableNPC) npc);
            });
            myGridPane.addRow(0, tradeButton);
        }
        if (npc instanceof RobbableNPC) {
            count++;
            this.robButton = new Button("Rob");
            robButton.setOnAction(event -> {
                controller.handleRobEvent((RobbableNPC) npc);
            });
            myGridPane.addRow(0, robButton);
        }

        if (npc instanceof IgnorableNPC) {
            count++;
            this.ignoreButton = new Button("Ignore");
            ignoreButton.setOnAction(e -> {
                controller.handleResumeTravelToDest("");
            });
            myGridPane.addRow(0, ignoreButton);
        }
        infoLabel = new Label(npc.getDescription() + " What would you like to do?");
        this.npcDisplay = new ImageView();
        root.getChildren().addAll(infoLabel, myGridPane);
        return root;
    }
}
