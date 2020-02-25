import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Map;

public class Ship {
    private StringProperty name;
    private IntegerProperty cargoCapacity;
    private Map<Item, InventoryEntry> itemInventory;
    private IntegerProperty fuelCapacity;
    private IntegerProperty health;

    public Ship(String name, int cargoCapacity, int fuelCapacity, int health) {
        this.name = new SimpleStringProperty(name);
        this.cargoCapacity = new SimpleIntegerProperty(cargoCapacity);
        this.fuelCapacity = new SimpleIntegerProperty(fuelCapacity);
        this.health = new SimpleIntegerProperty(health);
    }

    public Ship(Difficulty difficulty) {
        this("Player Ship",
                difficulty.getStartCargoCapacity(),
                difficulty.getStartFuelCapacity(),
                difficulty.getStartHealth()
        );
    }

    public int calcTotalItems() {
        int total = 0;
        for (InventoryEntry entry : itemInventory.values()) {
            total += entry.getQuantity();
        }

        return total;
    }

    public int getCargoCapacity() {
        return cargoCapacity.get();
    }

    public IntegerProperty cargoCapacityProperty() {
        return cargoCapacity;
    }

    public void setCargoCapacity(int cargoCapacity) {
        this.cargoCapacity.set(cargoCapacity);
    }

    public Map<Item, InventoryEntry> getItemInventory() {
        return itemInventory;
    }

    public void setItemInventory(Map<Item, InventoryEntry> itemInventory) {
        this.itemInventory = itemInventory;
    }

}
