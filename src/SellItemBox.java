import javafx.beans.binding.Bindings;

import java.util.Map;

public class SellItemBox extends ItemBox{

    public SellItemBox(Map.Entry<Item, StockEntry> entry) {
        super(entry);
//        getPriceLabel().setText(Integer.toString(entry.getValue().getBuyingPrice()));
//        getQuantityLabel().setText("0/" + entry.getValue().getQuantity());
//        getQuantityLabel().textProperty().bind(Bindings.format("%.0f/%s",
//                getSlider().valueProperty(), entry.getValue().getQuantity()));
    }
}
