package screens;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import uicomponents.*;
import cores.characters.Player;
import cores.Game;
import cores.tradingsystem.TransactionSystem;
import cores.tradingsystem.Marketplace;
import cores.objects.Entry;
import cores.objects.InventoryEntry;
import cores.objects.Item;
import cores.objects.StockEntry;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Map;

public class MarketScreen extends GameScreen {
    private Game game;
    private Player player;
    private Marketplace marketplace;
    private Map<Item, StockEntry> stock;
    private Map<Item, InventoryEntry> inventory;
    private ScrollPane scrollPane;
    private MyGridPane itemsPane;
    private MyGridPane contentPane;
    private Label infoLabel;
    private Label messageLabel;
    private TransactionSystem transactionSystem;
    private boolean buyingMode;

    public MarketScreen(Stage primaryStage, Game game, boolean buyingMode) {
        super(primaryStage,
                game,
                game.getPlayer().getCurrentRegion().getMarketplace().getName(),
                true);
        this.game = game;
        this.player = game.getPlayer();
        this.marketplace = player.getCurrentRegion().getMarketplace();
        this.stock = marketplace.getStock();
        this.inventory = player.getShip().getItemInventory();
        this.buyingMode = buyingMode;
        this.transactionSystem = new TransactionSystem(player, marketplace);
        getGame().getPlayer().currentRegionProperty().addListener(e -> {
            updateMarketInfo();
        });
    }

    @Override
    public Pane constructContentPane() {
        itemsPane = buyingMode ? constructBuyItemBoxesPane() : constructSellItemBoxesPane();

        scrollPane = new ScrollPane(itemsPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        messageLabel = new Label();

        infoLabel = new Label();
        infoLabel.textProperty().bind(Bindings.format("SHIP CAPACITY: %s/%s CREDIT: %S",
                player.getShip().totalItemsProperty(), player.getShip().cargoCapacityProperty(),
                player.creditsProperty()));
        contentPane = new MyGridPane(null, MyGridPane.getSpan(1));
        contentPane.addColumn(30, 0, scrollPane, messageLabel, infoLabel);
        return contentPane;
    }

    @Override
    public void doAfterScreenIsShown() {
        super.doAfterScreenIsShown();
        scrollPane.prefHeightProperty().bind(getContentHeight().subtract(60));
    }

    private void updateMarketInfo() {
        this.marketplace = player.getCurrentRegion().getMarketplace();
        this.stock = marketplace.getStock();
        setTitle(marketplace.getName());
        this.transactionSystem = new TransactionSystem(player, marketplace);
        this.itemsPane = constructBuyItemBoxesPane();
    }

    private MyGridPane constructBuyItemBoxesPane() {
        itemsPane = new MyGridPane(null, MyGridPane.getSpan(1));
        itemsPane.addColumn(0, constructHeaderPane());
        for (Map.Entry<Item, StockEntry> i : stock.entrySet()) {
            itemsPane.addColumn(0, new ItemBox(i));
        }
        itemsPane.addColumn(0, constructUpgradePane());
        return itemsPane;
    }

    private MyGridPane constructSellItemBoxesPane() {
        itemsPane = new MyGridPane(null, MyGridPane.getSpan(1));
        boolean haveItemsToSell = !inventory.isEmpty();
        if (haveItemsToSell) {
            itemsPane.addColumn(0, constructHeaderPane());
            for (Map.Entry<Item, InventoryEntry> i : inventory.entrySet()) {
                itemsPane.addColumn(0, new ItemBox(i));
            }
        } else {
            Label noItemLabel = new Label("You currently don't have any items to sell!");
            itemsPane.addColumn(0, new StackPane(noItemLabel));
        }
        return itemsPane;
    }

    private MyGridPane constructHeaderPane() {
        MyGridPane headerPane = new MyGridPane(MyGridPane.getSpan(1), new double[]{15, 15, 15, 45, 10});
        Label nameHeader = new Label("NAME");
        nameHeader.getStyleClass().add("h2");
        Label priceHeader = new Label("PRICE");
        priceHeader.getStyleClass().add("h2");
        Label playerQuantityHeader = new Label("SHIP STOCK");
        playerQuantityHeader.getStyleClass().add("h2");
        Label quantityHeader = new Label("QUANTITY");
        quantityHeader.getStyleClass().add("h2");

        headerPane.addRow(0, nameHeader, priceHeader, playerQuantityHeader, quantityHeader);
        return headerPane;
    }

    private MyGridPane constructUpgradePane() {
        MyGridPane upgradePane = new MyGridPane(MyGridPane.getSpan(1), new double[]{90, 10});
        Label upgradeLabel = new Label(!marketplace.hasBoughtUpgrade()
                ? "Special! " + marketplace.getUpgrade().toString()
                + " for " + marketplace.getUpgrade().getPrice() + " credits!"
                : "This market does not sell any player upgrades.");
        marketplace.boughtUpgradeProperty().addListener(e -> {
            upgradeLabel.setText(!marketplace.hasBoughtUpgrade()
                    ? "Special! " + marketplace.getUpgrade().toString()
                    + " for " + marketplace.getUpgrade().getPrice() + " credits!"
                    : "This market does not sell any player upgrades.");
        });
        Button buyUpgradeButton = new Button("Buy");
        buyUpgradeButton.setMaxWidth(9999);
        buyUpgradeButton.disableProperty().bind(marketplace.boughtUpgradeProperty());
        buyUpgradeButton.setOnAction(e -> {
            try {
                transactionSystem.buyUpgrade(marketplace.getUpgrade());
            } catch (IllegalAccessException ex) {
                messageLabel.setText(ex.getMessage());
            }
        });
        upgradePane.addRow(0, upgradeLabel, buyUpgradeButton);
        return upgradePane;
    }

    private class ItemBox extends MyGridPane {
        //private Map.cores.Entry<cores.Item, cores.StockEntry> entry;
        private Label nameLabel;
        private Label priceLabel;
        private Label shipStockLabel;
        private Label quantityLabel;
        private Slider slider;
        private Button button;

        public ItemBox(Map.Entry<Item, ? extends Entry> entry) {
            super(MyGridPane.getSpan(1), new double[]{15, 15, 15, 35, 10, 10});
            boolean availableAtMarket = stock.containsKey(entry.getKey());
            //this.entry = entry;
            nameLabel = new Label(entry.getKey().getName().toUpperCase());
            priceLabel = new Label();
            priceLabel = new Label(availableAtMarket ? Integer.toString(buyingMode
                    ? stock.get(entry.getKey()).getBuyingPrice()
                    : stock.get(entry.getKey()).getSellingPrice()) : "NOT SOLD");
            slider = new Slider(0, entry.getValue().getQuantity(), 0);
            slider.setBlockIncrement(1);
            slider.setMajorTickUnit(1);
            slider.setMinorTickCount(0);
            slider.setMaxWidth(9999);
            slider.setSnapToTicks(true);

            ObjectProperty<Map<Item, InventoryEntry>> inventory =
                    player.getShip().itemInventoryProperty();
            InventoryEntry shipEntry = inventory.get().get(entry.getKey());
            shipStockLabel = new Label(
                    "" + ((shipEntry == null) ? 0 : shipEntry.getQuantity()));

            inventory.addListener(((observable, oldValue, newValue) -> {
                InventoryEntry changedEntry = inventory.get().get(entry.getKey());
                if (changedEntry == null) {
                    shipStockLabel.setText("" + 0);
                } else {
                    shipStockLabel.setText("" + changedEntry.getQuantity());
                }
            }));


            quantityLabel = new Label("0/" + entry.getValue().getQuantity());
            quantityLabel.textProperty().bind(Bindings.format("%.0f/%s",
                    slider.valueProperty(), entry.getValue().quantityProperty()));
            button = new Button(buyingMode ? "Buy" : "Sell");
            button.setMaxWidth(9999);
            if (!availableAtMarket || entry.getValue().getQuantity() == 0) {
                button.setDisable(true);
                slider.setDisable(true);
            }
            if (buyingMode) {
                button.setOnAction(e -> {
                    try {
                        transactionSystem.buy(entry.getKey(), marketplace, (int) slider.getValue());
                        itemsPane = constructBuyItemBoxesPane();
                        scrollPane.setContent(itemsPane);
                    } catch (Exception exception) {
                        messageLabel.setText(exception.getMessage());
                    }
                });
            } else {
                button.setOnAction(e -> {
                    try {
                        transactionSystem.sell(entry.getKey(), marketplace, (int) slider.getValue());
                        itemsPane = constructSellItemBoxesPane();
                        scrollPane.setContent(itemsPane);
                    } catch (Exception exception) {
                        messageLabel.setText(exception.getMessage());
                    }
                });
            }
            this.addRow(0, nameLabel, priceLabel, shipStockLabel, slider, quantityLabel, button);
        }
    }
}
