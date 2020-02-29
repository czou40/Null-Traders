import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Map;

public class UpgradeScreen extends GameScreen {
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

    public UpgradeScreen(Stage primaryStage, Game game) {
        super(primaryStage,
                game,
                "Currently Equipped Upgrades",
                true);
        this.game = game;
        this.player = game.getPlayer();
        this.marketplace = player.getCurrentRegion().getMarketplace();
        this.stock = marketplace.getStock();
        this.inventory = player.getShip().getItemInventory();
        this.buyingMode = buyingMode;
        this.controller = new MarketScreenController(player, marketplace);
    }

    @Override
    public Pane constructContentPane() {
        Label pilLabel = new Label("Pilot Upgrade:");
        Label pilVal;
        if(player.getUpgrades()[0] == player.emptySlot) {
            pilVal = new Label("[None Installed");
        } else {
            pilVal = new Label(player.getUpgrades()[0].toString());
        }

        Label figLabel = new Label("Fighter Upgrade:");
        Label figVal;
        if(player.getUpgrades()[0] == player.emptySlot) {
            figVal = new Label("[None Installed");
        } else {
            figVal = new Label(player.getUpgrades()[1].toString());
        }

        Label merLabel = new Label("Merchant Upgrade:");
        Label merVal;
        if(player.getUpgrades()[0] == player.emptySlot) {
            merVal = new Label("[None Installed");
        } else {
            merVal = new Label(player.getUpgrades()[2].toString());
        }

        Label engLabel = new Label("Engineering Upgrade:");
        Label engVal;
        if(player.getUpgrades()[0] == player.emptySlot) {
            engVal = new Label("[None Installed");
        } else {
            engVal = new Label(player.getUpgrades()[3].toString());
        }

        return null;
    }
}
