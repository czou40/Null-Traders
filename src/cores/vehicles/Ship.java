package cores.vehicles;

import cores.places.Region;
import cores.settings.Difficulty;
import cores.objects.InventoryEntry;
import cores.objects.Item;
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
    private IntegerProperty fuel;
    private IntegerProperty fuelCapacity;
    private IntegerProperty health;
    private String image = "file:src/images/spaceships/0.png";
    private static final double MAX_FUEL_EFFICIENCY = 0.4;


    public Ship(String name, int cargoCapacity, int fuelCapacity, int health) {
        this.name = new SimpleStringProperty(name);
        this.totalItems = new SimpleIntegerProperty(0);
        this.itemInventory = new SimpleObjectProperty<>(new HashMap<>());
        this.cargoCapacity = new SimpleIntegerProperty(cargoCapacity);
        this.fuel = new SimpleIntegerProperty(fuelCapacity);
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

    @Deprecated
    public void setTotalItems(int totalItems) {
        this.totalItems.set(totalItems);
    }

    public int getCargoCapacity() {
        return cargoCapacity.get();
    }

    public IntegerProperty cargoCapacityProperty() {
        return cargoCapacity;
    }

    @Deprecated
    public void setCargoCapacity(int cargoCapacity) {
        this.cargoCapacity.set(cargoCapacity);
    }

    public Map<Item, InventoryEntry> getItemInventory() {
        return itemInventory.get();
    }

    public ObjectProperty<Map<Item, InventoryEntry>> itemInventoryProperty() {
        return itemInventory;
    }

    @Deprecated
    public void setItemInventory(Map<Item, InventoryEntry> itemInventory) {
        this.itemInventory.set(itemInventory);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getFuel() {
        return fuel.get();
    }

    public IntegerProperty fuelProperty() {
        return fuel;
    }

    @Deprecated
    public void setFuel(int fuel) {
        this.fuel.set(fuel);
    }

    public int getFuelCapacity() {
        return fuelCapacity.get();
    }

    public IntegerProperty fuelCapacityProperty() {
        return fuelCapacity;
    }

    public void setFuelCapacity(int fuelCapacity) {
        this.fuelCapacity.set(fuelCapacity);
    }

    public int getHealth() {
        return health.get();
    }

    public IntegerProperty healthProperty() {
        return health;
    }

    @Deprecated
    public void setHealth(int health) {
        this.health.set(health);
    }

    public void printInventory() {
        //USE FOR TESTING ONLY
        System.out.println("Item Stock For " + name.get() + "\n");
        for (Item item : getItemInventory().keySet()) {
            InventoryEntry entry = getItemInventory().get(item);

            System.out.println(item + ": ");
            System.out.println("Quantity: " + entry.getQuantity());
            System.out.println("Average Price: " + entry.getTotalCost() / entry.getQuantity());
            System.out.println();
        }
    }

    public void damage(int amount) {
        health.set(Math.max(health.get() - amount, 0));
    }

    private int calculateFuelCost(Region region1, Region region2, double pilotInfluence) {
        return (int) (region1.distanceTo(region2) / 10 * pilotInfluence * MAX_FUEL_EFFICIENCY);
    }

    public boolean ableToTravelTo(Region region1, Region region2, double pilotInfluence) {
        return getFuel() >= calculateFuelCost(region1, region2, pilotInfluence);
    }

    public void decrementFuel(Region region1, Region region2, double pilotInfluence) {
        this.setFuel(Math.max(getFuel() - calculateFuelCost(region1, region2, pilotInfluence), 0));
    }

    public void load(Item item, int price, int quantity) {
        InventoryEntry playerEntry = itemInventory.get().get(item);
        //update ship inventory
        if (playerEntry == null) {
            playerEntry = new InventoryEntry();
        }
        playerEntry.add(price, quantity);
        this.itemInventory.get().put(item, playerEntry);
        this.totalItems.set(this.totalItems.get() + quantity);
    }

    public void unload(Item item, int quantity) {
        InventoryEntry playerEntry = itemInventory.get().get(item);
        playerEntry.remove(quantity);
        if (playerEntry.getQuantity() <= 0) {
            this.itemInventory.get().remove(item);
        }
        this.totalItems.set(this.totalItems.get() - quantity);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
