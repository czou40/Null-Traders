import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.util.Map;
import java.util.Vector;

public class Ship {
    private StringProperty name;
    private IntegerProperty cargoCapacity;
    private Map<Item, StockEntry> itemInventory;
    private IntegerProperty fuelCapacity;
    private IntegerProperty health;

    public int getCargoCapacity() {
        return cargoCapacity.get();
    }

    public IntegerProperty cargoCapacityProperty() {
        return cargoCapacity;
    }

    public void setCargoCapacity(int cargoCapacity) {
        this.cargoCapacity.set(cargoCapacity);
    }

    public Map<Item, StockEntry> getItemInventory() {
        return itemInventory;
    }

    public void setItemInventory(Map<Item, StockEntry> itemInventory) {
        this.itemInventory = itemInventory;
    }

}
