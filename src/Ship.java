import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

public class Ship {
    private StringProperty name;
    private IntegerProperty cargoCapacity;
    private ObjectProperty<Map<Item, InventoryEntry>> itemInventory;
    private IntegerProperty totalItems;
    private IntegerProperty fuelCapacity;
    private IntegerProperty health;

    public Ship(String name, int cargoCapacity, int fuelCapacity, int health) {
        this.name = new SimpleStringProperty(name);
        this.totalItems = new SimpleIntegerProperty(0);
        this.itemInventory = new SimpleObjectProperty<>(new HashMap<>());
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

    public int getTotalItems() {
        return totalItems.get();
    }

    public IntegerProperty totalItemsProperty() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems.set(totalItems);
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
        return itemInventory.get();
    }

    public ObjectProperty<Map<Item, InventoryEntry>> itemInventoryProperty() {
        return itemInventory;
    }

    public void setItemInventory(Map<Item, InventoryEntry> itemInventory) {
        this.itemInventory.set(itemInventory);
    }

    public void printInventory() {
        //USE FOR TESTING ONLY
        System.out.println("Item Stock For " + name.get() + "\n");
        for (Item item : getItemInventory().keySet()) {
            InventoryEntry entry = getItemInventory().get(item);

            System.out.println(item + ": ");
            System.out.println("Quantity: " + entry.getQuantity());
            System.out.println("Average Price: " + entry.getAverageBuyingPrice());
            System.out.println();
        }
    }
}
