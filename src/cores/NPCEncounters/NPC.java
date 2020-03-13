package cores.NPCEncounters;

import cores.Game;
import javafx.stage.Stage;
import screens.EncounterScreen;
import screens.Screen;

public interface NPC {
    EncounterScreen getEncounterScreen(Game game, Stage primaryStage);
}
