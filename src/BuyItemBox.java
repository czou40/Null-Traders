import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.util.Map;

public class BuyItemBox extends MyGridPane{
    private Map.Entry<Item, StockEntry> entry;
    private Label nameLabel;
    private Label priceLabel;
    private Label quantityLabel;
    //private Slider slider;
    private Button button;
    private MarketScreenController controller;

    public BuyItemBox(Map.Entry<Item, StockEntry> entry, MarketScreenController controller, Marketplace market) {
        super(MyGridPane.getSpan(1), new double[]{15, 15, 50, 10, 10});
        this.setMinHeight(50);
        this.entry = entry;
        this.controller = controller;
        nameLabel = new Label(entry.getKey().getName());
        priceLabel = new Label();
        priceLabel = new Label(Integer.toString(entry.getValue().getBuyingPrice()));
        /*
        slider = new Slider(0, entry.getValue().getQuantity(), 0);
        slider.setBlockIncrement(1);
        slider.setMaxWidth(9999);
        slider.maxProperty().bind(entry.getValue().quantityProperty());

         */
        quantityLabel = new Label();
        quantityLabel = new Label("" + entry.getValue().getQuantity());
        quantityLabel.textProperty().bind(Bindings.format("%s",
                /*slider.valueProperty(), */entry.getValue().quantityProperty()));
        button = new Button("Buy");
        button.setMaxWidth(9999);
        button.setOnAction(e -> {
            controller.buy(entry.getKey(), market);
        });
        this.addRow(0, nameLabel, priceLabel, quantityLabel, button);
    }

    public Map.Entry<Item, StockEntry> getEntry() {
        return entry;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public Label getPriceLabel() {
        return priceLabel;
    }

    public Label getQuantityLabel() {
        return quantityLabel;
    }

    /*
    public Slider getSlider() {
        return slider;
    }
     */

    public Button getButton() {
        return button;
    }
}