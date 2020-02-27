import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sun.text.resources.no.CollationData_no;

import java.util.Map;
import java.util.Stack;

public class MarketScreen extends GameScreen {
    private Game game;
    private Player player;
    private Marketplace marketplace;
    private Map<Item, StockEntry> stock;
    private Map<Item, InventoryEntry> inventory;
    private MyGridPane headerPane;
    private ScrollPane scrollPane;
    private MyGridPane itemsPane;
    private MarketScreenController controller;
    private boolean buyingMode;

    public MarketScreen(Stage primaryStage, Game game, boolean buyingMode) {
        super(primaryStage, game, game.getPlayer().getCurrentRegion().getMarketplace().getName(), true);
        this.game = game;
        this.player = game.getPlayer();
        this.marketplace = player.getCurrentRegion().getMarketplace();
        this.stock = marketplace.getStock();
        this.inventory = player.getShip().getItemInventory();
        this.buyingMode = buyingMode;
        this.controller = new MarketScreenController(player, marketplace);
        getGame().getPlayer().currentRegionProperty().addListener(e -> {
            updateMarketInfo();
        });
    }

    @Override
    public Pane constructContentPane() {
        Label nameHeader = new Label("NAME");
        Label priceHeader = new Label("PRICE");
        Label quantityHeader = new Label("QUANTITY");
        headerPane = new MyGridPane(MyGridPane.getSpan(1), new double[]{15, 15, 50, 10, 10});
        headerPane.addRow(0, nameHeader, priceHeader, quantityHeader);

        itemsPane = buyingMode ? constructBuyItemBoxesPane() : constructSellItemBoxesPane();
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

    private void updateMarketInfo() {
        this.marketplace = player.getCurrentRegion().getMarketplace();
        this.stock = marketplace.getStock();
        setTitle(marketplace.getName());
        this.controller = new MarketScreenController(player, marketplace);
        this.itemsPane = constructBuyItemBoxesPane();
    }

    private MyGridPane constructBuyItemBoxesPane() {
        itemsPane = new MyGridPane(MyGridPane.getSpan(10), MyGridPane.getSpan(1));
        itemsPane.addColumn(0, headerPane);
        for (Map.Entry<Item, StockEntry> i : stock.entrySet()) {
            itemsPane.addColumn(0, new ItemBox(i));
        }
        return itemsPane;
    }

    private MyGridPane constructSellItemBoxesPane() {
        itemsPane = new MyGridPane(MyGridPane.getSpan(10), MyGridPane.getSpan(1));
        boolean haveItemsToSell = !inventory.isEmpty();
        if (haveItemsToSell) {
            itemsPane.addColumn(0, headerPane);
            for (Map.Entry<Item, InventoryEntry> i : inventory.entrySet()) {
                itemsPane.addColumn(0, new ItemBox(i));
            }
        } else {
            Label noItemLabel = new Label("You currently don't have any items to sell!");
            itemsPane.addColumn(0, new StackPane(noItemLabel));
        }
        return itemsPane;
    }

    @Override
    public void adjustUIAfterScreenIsShown() {
        scrollPane.prefWidthProperty().bind(getContentWidth());
    }

    public MarketScreenController getController() {
        return controller;
    }

    private class ItemBox extends MyGridPane{
        //private Map.Entry<Item, StockEntry> entry;
        private Label nameLabel;
        private Label priceLabel;
        private Label quantityLabel;
        private Slider slider;
        private Button button;

        public ItemBox(Map.Entry<Item, ? extends Entry> entry) {
            super(MyGridPane.getSpan(1), new double[]{15, 15, 50, 10, 10});
            this.setMinHeight(50);
            //this.entry = entry;
            nameLabel = new Label(entry.getKey().getName());
            priceLabel = new Label();
            priceLabel = new Label(Integer.toString(buyingMode
                    ? stock.get(entry.getKey()).getBuyingPrice()
                    : stock.get(entry.getKey()).getSellingPrice()));
            slider = new Slider(0, entry.getValue().getQuantity(), 0);
            slider.setBlockIncrement(1);
            slider.setMaxWidth(9999);
            quantityLabel = new Label();
            quantityLabel = new Label("0/" + entry.getValue().getQuantity());
            quantityLabel.textProperty().bind(Bindings.format("%.0f/%s",
                    slider.valueProperty(), entry.getValue().getQuantity()));
            button = new Button(buyingMode ? "Buy" : "Sell");
            button.setMaxWidth(9999);
            if (buyingMode) {
                button.setOnAction(e -> {
                    controller.buy(entry.getKey(), marketplace);
                });
            } else {
                button.setOnAction(e -> {
                    controller.sell(entry.getKey(), marketplace);
                    itemsPane = constructBuyItemBoxesPane();
                    scrollPane.setContent(itemsPane);
                });
            }
            this.addRow(0, nameLabel, priceLabel, slider, quantityLabel, button);
        }
    }
}
