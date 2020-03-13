package cores.NPCEncounters;

import cores.characters.Player;

public class EncounterFactory {

    public static NPC generateRandomEncounter(Player player) {
        return new Bandit();
    }
}
