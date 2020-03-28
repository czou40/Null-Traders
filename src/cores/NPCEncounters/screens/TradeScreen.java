package cores.NPCEncounters.screens;

import cores.Game;
import cores.NPCEncounters.EncounterController;
import cores.NPCEncounters.TradableNPC;
import cores.objects.InventoryEntry;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import screens.Screen;
import uicomponents.MyGridPane;

public class TradeScreen extends Screen {

    private TradableNPC npc;
    private EncounterController controller;

    private MyGridPane gridPane;
    private Button dealButton;
    private Label nameLeftLabel;
    private Label nameLabel;
    private Label priceLeftLabel;
    private Label priceLabel;
    private Label quantityLeftLabel;
    private Label quantityLabel;
    private Button negotiateButton;
    private Label transactionInfoLabel;
    private Button continueButton;
    private Button giveUpButton;
    private HBox buttonsPane;

    public TradeScreen(Stage primaryStage, Game game, TradableNPC npc,
                       EncounterController controller) {
        super(primaryStage, game);
        this.npc = npc;
        this.controller = controller;
    }

    @Override
    public Pane constructRoot() {
        gridPane = new MyGridPane(null, MyGridPane.getSpan(1));

        MyGridPane labelsPane = new MyGridPane(null, new double[]{30, 70});
        nameLeftLabel = new Label("NAME");
        priceLeftLabel = new Label("PRICE");
        quantityLeftLabel = new Label("QUANTITY");
        nameLabel = new Label(npc.getItem().getName());
        quantityLabel = new Label(String.valueOf(npc.getQuantity()));
        priceLabel = new Label(String.valueOf(npc.getPrice()));
        labelsPane.addColumn(0, nameLeftLabel, quantityLeftLabel, priceLeftLabel);
        labelsPane.addColumn(1, nameLabel, quantityLabel, priceLabel);

        buttonsPane = new HBox(20);
        dealButton = new Button("Deal");
        dealButton.setOnAction(event -> {
            controller.handleBuyEvent(this, npc);
        });
        negotiateButton = new Button("Negotiate");
        negotiateButton.setOnAction(event -> {
            controller.handleNegotiateEvent(this, npc);
        });
        continueButton = new Button("Continue");
        continueButton.setOnAction(e -> {
            controller.handleResumeTravelToDest("You had a wonderful experience with the trader.");
        });
        giveUpButton = new Button("Give Up");
        giveUpButton.setOnAction(event -> {
            controller.handleResumeTravelToDest("You chose not to trade with the trader.");
        });
        buttonsPane.getChildren().addAll(dealButton, negotiateButton, giveUpButton);
        InventoryEntry playerEntry =
                game.getPlayer().getShip().getItemInventory().get(npc.getItem());
        int playerHas = playerEntry == null ? 0 : playerEntry.getQuantity();
        double averageCost =
                playerEntry == null ? 0 : 1.0 * playerEntry.getTotalCost() / playerHas;
        transactionInfoLabel = new Label(String.format(
                "You currently have %d %s(s), which cost you %.1f credits each.",
                playerHas, npc.getItem().getName(), averageCost));
        gridPane.addColumn(0, labelsPane, transactionInfoLabel, buttonsPane);
        return gridPane;
    }

    public void update() {
        quantityLabel.setText(String.valueOf(npc.getQuantity()));
        priceLabel.setText(String.valueOf(npc.getPrice()));
    }

    public void displayTransactionComplete() {
        InventoryEntry playerEntry =
                game.getPlayer().getShip().getItemInventory().get(npc.getItem());
        int playerHas = playerEntry.getQuantity();
        transactionInfoLabel.setText(String.format(
                "Transaction successful! Now you have %d %s(s)",
                playerHas, npc.getItem().getName()));
        gridPane.getChildren().remove(buttonsPane);
        gridPane.addColumn(0, continueButton);
    }

    public void displayTransactionFailed(String reason) {
        negotiateButton.setDisable(true);
        transactionInfoLabel.setText("Transaction failed! " + reason);
    }

    public void displayNegotiationSuccessful() {
        negotiateButton.setDisable(true);
        transactionInfoLabel.setText("All right... Maybe I can lower the price a little bit.");
    }

    public void displayNegotiationFailed() {
        negotiateButton.setDisable(true); //chunhao i did this so you can't renegotiate -jared
        transactionInfoLabel.setText("Screw you! No deal!");
    }
}
