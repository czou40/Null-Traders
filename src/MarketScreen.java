import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.text.resources.no.CollationData_no;

import java.util.Map;

public class MarketScreen extends GameScreen {
    private Game game;
    private Player player;
    private Marketplace marketplace;
    private Map<Item, StockEntry> stock;
    private ScrollPane scrollPane;
    private MarketScreenController controller;

    public MarketScreen(Stage primaryStage, Game game) {
        super(primaryStage, game, game.getPlayer().getCurrentRegion().getMarketplace().getName(), true);
        this.game = game;
        this.player = game.getPlayer();
        updateRegion();
        getGame().getPlayer().currentRegionProperty().addListener(e -> {
            updateRegion();
        });
    }

    @Override
    public Pane constructContentPane() {
        Label nameHeader = new Label("Name");
        Label priceHeader = new Label("Price");
        Label quantityHeader = new Label("Quantity");
        MyGridPane headerPane = new MyGridPane(MyGridPane.getSpan(1), new double[]{15, 15, 50, 10, 10});
        headerPane.addRow(0, nameHeader, priceHeader, quantityHeader);

        MyGridPane itemsPane = new MyGridPane(MyGridPane.getSpan(10), MyGridPane.getSpan(1));
        itemsPane.addColumn(0, headerPane);
        for (Map.Entry<Item, StockEntry> i : stock.entrySet()) {
            itemsPane.addColumn(0, new BuyItemBox(i));
        }

        scrollPane = new ScrollPane(itemsPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Label creditsLabel = new Label();
        Label shipCapacityLabel = new Label();
        shipCapacityLabel.textProperty().bind(Bindings.format("SHIP CAPACITY: %s/%s",
                player.getShip().getTotalItems(), player.getShip().getCargoCapacity()));
        creditsLabel.textProperty().bind(Bindings.format("CREDITS: %s", player.creditsProperty()));
        MyGridPane playerInfoPane = new MyGridPane(MyGridPane.getSpan(1), new double[]{30, 30});
        playerInfoPane.addRow(0, creditsLabel, shipCapacityLabel);
        MyGridPane contentPane = new MyGridPane(new double[]{90, 10}, MyGridPane.getSpan(1));
        contentPane.addColumn(0, scrollPane, playerInfoPane);
        return contentPane;
    }

    private void updateRegion() {
        this.marketplace = game.getPlayer().getCurrentRegion().getMarketplace();
        this.stock = marketplace.getStock();
        setTitle(marketplace.getName());
        this.controller = new MarketScreenController(player, marketplace);
    }

    @Override
    public void adjustUIAfterScreenIsShown() {
        scrollPane.prefWidthProperty().bind(getContentWidth());
    }

    private class BuyItemBox extends MyGridPane{
        private Map.Entry<Item, StockEntry> entry;
        private Label nameLabel;
        private Label priceLabel;
        private Label quantityLabel;
        private Slider slider;
        private Button button;

        public BuyItemBox(Map.Entry<Item, StockEntry> entry) {
            super(MyGridPane.getSpan(1), new double[]{15, 15, 50, 10, 10});
            this.setMinHeight(50);
            this.entry = entry;
            nameLabel = new Label(entry.getKey().getName());
            priceLabel = new Label();
            priceLabel = new Label(Integer.toString(entry.getValue().getBuyingPrice()));
            slider = new Slider(0, entry.getValue().getQuantity(), 0);
            slider.setBlockIncrement(1);
            slider.setMaxWidth(9999);
            quantityLabel = new Label();
            quantityLabel = new Label("0/" + entry.getValue().getQuantity());
            quantityLabel.textProperty().bind(Bindings.format("%.0f/%s",
                    slider.valueProperty(), entry.getValue().getQuantity()));
            button = new Button("Buy");
            button.setMaxWidth(9999);
            button.setOnAction(e -> {
                controller.buy(entry.getKey(), marketplace);
            });
            this.addRow(0, nameLabel, priceLabel, slider, quantityLabel, button);
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

        public Slider getSlider() {
            return slider;
        }

        public Button getButton() {
            return button;
        }
    }
}
