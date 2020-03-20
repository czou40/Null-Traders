package cores.NPCEncounters.Screens;

import cores.Game;
import cores.NPCEncounters.EncounterController;
import cores.NPCEncounters.FightableNPC;
import cores.NPCEncounters.NPC;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import screens.Screen;
import javafx.scene.control.Button;
import uicomponents.FightAnimationPane;
import uicomponents.MyGridPane;
import uicomponents.MyProgressBar;

import javax.swing.text.html.ImageView;
import java.awt.*;

public class FightScreen extends Screen {
    private FightableNPC npc;
    private EncounterController controller;
    private Pane root;
    private FightAnimationPane animationPane;

    public FightScreen(Stage primaryStage, Game game, FightableNPC npc, EncounterController controller) {
        super(primaryStage, game);
        this.npc = npc;
        this.controller = controller;
    }

    @Override
    public Pane constructRoot() {
        animationPane = new FightAnimationPane(getGame().getPlayer().getShip().getImage(), NPC.getImage());
        root = new Pane(animationPane);
        return root;
    }

    @Override
    public void doAfterScreenIsShown() {
        super.doAfterScreenIsShown();
        animationPane.adjustImagePosition(getRootWidth(), getRootHeight());
        animationPane.loopAnimation();
    }
}
