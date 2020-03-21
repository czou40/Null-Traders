package cores.NPCEncounters;

import javafx.util.Pair;

public interface RobbableNPC extends NPC{
    public Pair<Boolean, String> handleRob(); //return if rob was successful
}
