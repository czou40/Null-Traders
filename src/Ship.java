import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.util.Vector;

public class Ship {
    private StringProperty name;
    private IntegerProperty cargoCapacity;
    private Vector<Item> itemInventory;
    private IntegerProperty fuelCapacity;
    private IntegerProperty health;

}
