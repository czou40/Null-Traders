package cores.NPCEncounters;

import cores.characters.Player;

public interface RobbableNPC extends NPC{
    public boolean handleRob(); //return if rob was successful
}
