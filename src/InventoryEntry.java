import javafx.beans.property.IntegerProperty;

public class InventoryEntry implements Entry {
    private IntegerProperty quantity;
    private IntegerProperty avgBuyingPrice;


    public void add(int buyingPrice) {
        quantity.set(quantity.get() + 1);

        avgBuyingPrice.set((avgBuyingPrice.get() * (quantity.get() - 1) + buyingPrice) / quantity.get());
    }

    public void remove() {
        quantity.set(quantity.get() - 1);
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

    public int getAvgSellingPrice() {
        return avgBuyingPrice.get();
    }

    public IntegerProperty avgSellingPriceProperty() {
        return avgBuyingPrice;
    }
}
