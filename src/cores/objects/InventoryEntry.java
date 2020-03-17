package cores.objects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class InventoryEntry implements Entry {
    private IntegerProperty quantity;
    private DoubleProperty totalCost;

    public InventoryEntry() {
        quantity = new SimpleIntegerProperty();
        totalCost = new SimpleDoubleProperty();
    }


    public void add(int buyingPrice, int number) {
        quantity.set(quantity.get() + number);
        totalCost.set(totalCost.get() + buyingPrice * number);
    }

    public void remove(int number) {
        int original = quantity.get();
        quantity.set(original - number);
        totalCost.set(totalCost.get() * quantity.get() / original);
    }

    @Override
    public int getQuantity() {
        return quantity.get();
    }

    @Override
    public IntegerProperty quantityProperty() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public double getAverageBuyingPrice() {
        return (0.0 + getTotalCost()) / getQuantity();
    }

    public double getTotalCost() {
        return totalCost.get();
    }

    public DoubleProperty totalCostProperty() {
        return totalCost;
    }

    @Deprecated
    public void setTotalCost(int totalCost) {
        this.totalCost.set(totalCost);
    }
}
