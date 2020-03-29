package cores.NPCEncounters;

import cores.characters.Player;
import cores.objects.InventoryEntry;
import cores.objects.Item;
import cores.places.Region;
import cores.settings.Difficulty;


import java.util.Map;


public class EncounterFactory {
    private static final boolean ENCOUNTERSALWAYS = false; //true only for testing

    public static NPC generateRandomEncounter(Player player, Difficulty difficulty, Region dest) {
        double rand = Math.random(); //0.1 for bandit, 0.4 for trade, 0.9 for police
        double encounterChance;
        if (ENCOUNTERSALWAYS) {
            encounterChance = 1;
        } else {
            encounterChance = difficulty.getEncounterChance();
        }

        NPC encounter = null;
        if (rand < encounterChance / 3) {
            encounter = new Bandit(player, dest);
        } else if (rand < 2 * encounterChance / 3) {
            encounter = new Trader(player);
        } else if (rand < encounterChance) {
            Map<Item, InventoryEntry> itemInventory = player.getShip().getItemInventory();
            for (Item item : itemInventory.keySet()) {
                if (item.isIllegal()) {
                    encounter = new Police(player, item, dest);
                }
            }
        }

        return encounter;
    }
}
