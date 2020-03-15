package cores.objects;

import javafx.beans.property.IntegerProperty;

public interface Entry {
    int getQuantity();

    IntegerProperty quantityProperty();

    void setQuantity(int quantity);

    int getBuyingPrice();
}
