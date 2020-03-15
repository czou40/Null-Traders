package screens;

import cores.Game;
import cores.NPCEncounters.*;
import cores.places.Region;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uicomponents.MyGridPane;
import uicomponents.MyNavigationButton;

public class EncounterScreen extends Screen {
    private NPC npc;
    private Region dest;
    private MyNavigationButton fightButton;
    private MyNavigationButton fleeButton;
    private MyNavigationButton tradeButton;
    private MyNavigationButton ignoreButton;
    private MyNavigationButton robButton;
    private ImageView npcDisplay;
    private Label infoLabel;
    private EncounterController encounterController;

    public EncounterScreen(Stage primaryStage, Game game, NPC npc, Region dest) {
        super(primaryStage, game);
        int count = 0;
        this.npc = npc;
        this.dest = dest;
    }

    @Override
    public Pane constructRoot() {
        MyGridPane myGridPane = new MyGridPane();
        int count = 0;
        if (npc instanceof Fightable) {
            count += 2;
            this.fightButton = new MyNavigationButton("Fight", null);
            this.fleeButton = new MyNavigationButton("Flee", null);
            myGridPane.addRow(1, fightButton, fleeButton);
        }
        if (npc instanceof ITrade) {
            count++;
            this.tradeButton = new MyNavigationButton("Trade", null);
            myGridPane.addRow(1, tradeButton);
        }
        if (npc instanceof Robbable) {
            count++;
            this.robButton = new MyNavigationButton("Rob", null);
            myGridPane.addRow(1, robButton);
        }

        if (npc instanceof Ignorable) {
            count++;
            this.ignoreButton = new MyNavigationButton("Ignore",
                    new NothingHappenedScreen(getPrimaryStage(), getGame(), dest.getName()));
            myGridPane.addRow(1, ignoreButton);
        }
        infoLabel = new Label("You encountered a " + npc.getClass().getSimpleName()
                + "!\n" + "what would you like to do?");
        this.npcDisplay = new ImageView();
        myGridPane.add(infoLabel, 0, 0, 1, 4);
        myGridPane.setColumnConstraints(MyGridPane.getSpan(count), HPos.CENTER);
        return myGridPane;
    }
}
