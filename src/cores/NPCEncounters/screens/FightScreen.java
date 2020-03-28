package cores.NPCEncounters.screens;

import cores.Game;
import cores.NPCEncounters.EncounterController;
import cores.NPCEncounters.FightableNPC;
import cores.NPCEncounters.NPC;
import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import screens.Screen;
import uicomponents.FightAnimationPane;
import uicomponents.MyGridPane;

public class FightScreen extends Screen {
    private FightableNPC npc;
    private EncounterController controller;
    private MyGridPane root;
    private FightAnimationPane animationPane;
    private Label messageLabel;
    private Button continueButton;

    public FightScreen(Stage primaryStage, Game game, FightableNPC npc,
                       EncounterController controller) {
        super(primaryStage, game);
        this.npc = npc;
        this.controller = controller;
    }

    @Override
    public Pane constructRoot() {
        animationPane = new FightAnimationPane(getGame().getPlayer().getShip().getImage(),
                NPC.getImage());
        messageLabel = new Label();
        messageLabel.setVisible(false);
        continueButton = new Button("OK");
        continueButton.setVisible(false);
        continueButton.setOnAction(event -> {
            controller.handleResumeTravelToDest("");
        });
        root = new MyGridPane(null, MyGridPane.getSpan(1));
        root.addColumn(0, animationPane, messageLabel, continueButton);
        return root;
    }

    @Override
    public void doAfterScreenIsShown() {
        super.doAfterScreenIsShown();
        animationPane.setTranslateX(-50);
        animationPane.adjustImagePosition(getRootWidth());
        animationPane.loopAnimation();
        PauseTransition transition = new PauseTransition(Duration.seconds(4));
        transition.setOnFinished(event -> {
            animationPane.stopAnimation();
            controller.handleFightEvent(this, npc);
        });
        transition.play();
    }

    public void update(String message) {
        messageLabel.setText(message);
        continueButton.setVisible(true);
        messageLabel.setVisible(true);
    }
}
