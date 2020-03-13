package cores.NPCEncounters;

import cores.characters.Player;
import cores.objects.InventoryEntry;
import cores.objects.Item;
import cores.settings.Difficulty;

import java.util.Map;
import java.util.Random;

public class EncounterFactory {

    public static NPC generateRandomEncounter(Player player, Difficulty difficulty) {
        double rand = Math.random();
        double encounterChance = difficulty.getEncounterChance();

        NPC encounter = null;
        if (rand < encounterChance / 3) {
            encounter = new Bandit(player);
        } else if (rand < 2 * encounterChance / 3) {
            encounter = new Trader(player);
        } else if (rand < encounterChance) {
            Map<Item, InventoryEntry> itemInventory = player.getShip().getItemInventory();
            for (Item item : itemInventory.keySet()) {
                if (item.isIllegal()) {
                    encounter = new Police(player, item);
                }
            }
        }

        return encounter;
    }
}
