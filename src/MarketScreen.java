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
    private MyGridPane headerPane;
    private ScrollPane scrollPane;
    private MyGridPane itemsPane;
    private Label messageLabel;
    private MarketScreenController controller;
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
        this.controller = new MarketScreenController(player, marketplace);
        getGame().getPlayer().currentRegionProperty().addListener(e -> {
            updateMarketInfo();
        });
    }

    @Override
    public Pane constructContentPane() {
        Label nameHeader = new Label("NAME");
        Label priceHeader = new Label("PRICE");
        Label playerQuantityHeader = new Label("SHIP STOCK");
        Label quantityHeader = new Label("QUANTITY");
        headerPane = new MyGridPane(MyGridPane.getSpan(1), new double[]{13, 13, 13, 50, 10});
        headerPane.addRow(0, nameHeader, priceHeader, playerQuantityHeader, quantityHeader);
        headerPane.setMinHeight(50);
        itemsPane = buyingMode ? constructBuyItemBoxesPane() : constructSellItemBoxesPane();
        //itemsPane = new MyGridPane(MyGridPane.getSpan(10), MyGridPane.getSpan(1));
        //itemsPane.addColumn(0, headerPane);
        //for (Map.Entry<Item, StockEntry> i : stock.entrySet()) {
        //itemsPane.addColumn(0, new BuyItemBox(i, controller, marketplace));
        //}

        scrollPane = new ScrollPane(itemsPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        //scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        messageLabel = new Label();

        Label creditsLabel = new Label();
        Label shipCapacityLabel = new Label();
        shipCapacityLabel.textProperty().bind(Bindings.format("SHIP CAPACITY: %s/%s",
                player.getShip().totalItemsProperty(), player.getShip().cargoCapacityProperty()));
        creditsLabel.textProperty().bind(Bindings.format("CREDITS: %s", player.creditsProperty()));
        
        MyGridPane upgradePane = new MyGridPane(MyGridPane.getSpan(1), new double[]{90, 10});
        if (buyingMode) {
            Label upgradeLabel = new Label(!marketplace.hasBoughtUpgrade()
                    ? "Special! " + marketplace.getUpgrade().toString()
                    + " for " + marketplace.getUpgrade().getPrice() + " credits!"
                    : "This market does not sell any play upgrades.");
            marketplace.boughtUpgradeProperty().addListener(e -> {
                upgradeLabel.setText(!marketplace.hasBoughtUpgrade()
                        ? "Special! " + marketplace.getUpgrade().toString()
                        + " for " + marketplace.getUpgrade().getPrice() + " credits!"
                        : "This market does not sell any play upgrades.");
            });
            Button buyUpgradeButton = new Button("Buy");
            buyUpgradeButton.setMaxWidth(9999);
            buyUpgradeButton.disableProperty().bind(marketplace.boughtUpgradeProperty());
            buyUpgradeButton.setOnAction(e -> {
                try {
                    controller.buyUpgrade(marketplace.getUpgrade());
                } catch (IllegalAccessException ex) {
                    messageLabel.setText(ex.getMessage());
                }
            });
            upgradePane.addRow(0, upgradeLabel, buyUpgradeButton);
        }
        MyGridPane playerInfoPane = new MyGridPane(MyGridPane.getSpan(1), new double[]{30, 30});
        playerInfoPane.addRow(0, creditsLabel, shipCapacityLabel);
        MyGridPane contentPane
                = new MyGridPane(new double[]{70, 10, 10, 10}, MyGridPane.getSpan(1));
        contentPane.addColumn(0, scrollPane, upgradePane, messageLabel, playerInfoPane);
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
        itemsPane = new MyGridPane(null, MyGridPane.getSpan(1));
        itemsPane.addColumn(0, headerPane);
        for (Map.Entry<Item, StockEntry> i : stock.entrySet()) {
            itemsPane.addColumn(0, new ItemBox(i));
        }
        return itemsPane;
    }

    private MyGridPane constructSellItemBoxesPane() {
        itemsPane = new MyGridPane(null, MyGridPane.getSpan(1));
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
    /*
    public void removeItem(Item item) {
        //search for and remove the item
        System.out.println("removing item");
        for (Node node : itemsPane.getChildren()) {
            System.out.println(node);
            if (node instanceof BuyItemBox) {
                System.out.println("found item box");
                BuyItemBox itemBox = (BuyItemBox) node;
                if (itemBox.getNameLabel().getText().equals(item.getName())) {
                    System.out.println("Item box has same name");
                    itemsPane.getChildren().remove(itemBox);
                    break;
                }
            }
        }
    }

    private void updateRegion() {
        this.marketplace = game.getPlayer().getCurrentRegion().getMarketplace();
        this.stock = marketplace.getStock();
        setTitle(marketplace.getName());
        this.controller = new MarketScreenController(player, marketplace, this);
    }



    @Override
    public void adjustUIAfterScreenIsShown() {
        scrollPane.prefWidthProperty().bind(getContentWidth());
    }

    public MarketScreenController getController() {
        return controller;
    }

     */

    private class ItemBox extends MyGridPane {
        //private Map.Entry<Item, StockEntry> entry;
        private Label nameLabel;
        private Label priceLabel;
        private Label shipStockLabel;
        private Label quantityLabel;
        private Slider slider;
        private Button button;

        public ItemBox(Map.Entry<Item, ? extends Entry> entry) {
            super(MyGridPane.getSpan(1), new double[]{15, 15, 15, 50, 10, 10});
            this.setMinHeight(50);
            boolean availableAtMarket = stock.containsKey(entry.getKey());
            //this.entry = entry;
            nameLabel = new Label(entry.getKey().getName());
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
                        controller.buy(entry.getKey(), marketplace, (int) slider.getValue());
                        itemsPane = constructBuyItemBoxesPane();
                        scrollPane.setContent(itemsPane);
                    } catch (Exception exception) {
                        messageLabel.setText(exception.getMessage());
                    }
                });
            } else {
                button.setOnAction(e -> {
                    try {
                        controller.sell(entry.getKey(), marketplace, (int) slider.getValue());
                        itemsPane = constructSellItemBoxesPane();
                        scrollPane.setContent(itemsPane);
                    } catch (Exception exception) {
                        messageLabel.setText(exception.getMessage());
                    }
                });
            }
            this.addRow(0, nameLabel, priceLabel, shipStockLabel, slider, quantityLabel, button);
        }
        /*
        public BuyItemBox(Map.Entry<Item, StockEntry> entry, MarketScreenController controller,
        Marketplace market) {
            super(MyGridPane.getSpan(1), new double[]{15, 15, 50, 10, 10});
            this.setMinHeight(50);
            this.entry = entry;
            this.controller = controller;
            nameLabel = new Label(entry.getKey().getName());
            priceLabel = new Label();
            priceLabel = new Label(Integer.toString(entry.getValue().getBuyingPrice()));

        slider = new Slider(0, entry.getValue().getQuantity(), 0);
        slider.setBlockIncrement(1);
        slider.setMaxWidth(9999);
        slider.maxProperty().bind(entry.getValue().quantityProperty());


            quantityLabel = new Label();
            quantityLabel = new Label("" + entry.getValue().getQuantity());
            quantityLabel.textProperty().bind(Bindings.format("%s",
                    slider.valueProperty(), entry.getValue().quantityProperty()));
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


        public Slider getSlider() {
            return slider;
        }


        public Button getButton() {
            return button;
        }
        */
    }
}
