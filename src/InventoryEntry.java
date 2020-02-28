import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class InventoryEntry implements Entry {
    private IntegerProperty quantity;
    private IntegerProperty totalCost;

    public InventoryEntry() {
        quantity = new SimpleIntegerProperty();
        totalCost = new SimpleIntegerProperty();
    }


    public void add(int buyingPrice, int number) {
        quantity.set(quantity.get() + number);
        totalCost.set(totalCost.get() + buyingPrice * number);
    }

    public void remove(int number) {
        quantity.set(quantity.get() - number);
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

    public int getTotalCost() {
        return totalCost.get();
    }

    public IntegerProperty totalCostProperty() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost.set(totalCost);
    }

    public double getAverageBuyingPrice() {
        return (0.0 + getTotalCost()) / getQuantity();
    }
}
