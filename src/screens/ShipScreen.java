package screens;

import cores.Game;
import cores.characters.Player;
import cores.objects.InventoryEntry;
import cores.objects.Item;
import cores.vehicles.Ship;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uicomponents.ButtonScaleHover;
import uicomponents.MyGridPane;
import uicomponents.MyProgressBar;

public class ShipScreen extends GameScreen {

    public ShipScreen(Stage primaryStage, Game game) {
        super(primaryStage, game, game.getPlayer().getShip().getName(), true);
    }

    @Override
    public Pane constructContentPane() {
        Player player = game.getPlayer();
        Ship ship = player.getShip();
        MyGridPane contentGridPane
                = new MyGridPane(null, new double[] {30, 70});

        Label difficultyLeftLabel = new Label("DIFFICULTY");
        Label difficultyRightLabel = new Label(game.getDifficulty().toString());

        Label nameLeftLabel = new Label("NAME");
        Label nameRightLabel = new Label();
        nameRightLabel.textProperty().bind(Bindings.format("%s", ship.nameProperty()));

        Label cargoCapacityLeftLabel = new Label("CARGO CAPACITY");
        Label cargoCapacityRightLabel = new Label();
        cargoCapacityRightLabel.textProperty().bind(Bindings.format("%d", ship.cargoCapacityProperty()));

        Label totalItemsLeftLabel = new Label("TOTAL ITEMS");
        Label totalItemsRightLabel = new Label();
        totalItemsRightLabel.textProperty().bind(Bindings.format("%d", ship.totalItemsProperty()));

        Label fuelLeftLabel = new Label("FUEL");
        Label fuelRightLabel = new Label();
        fuelRightLabel.textProperty().bind(Bindings.format("%d", ship.fuelProperty()));

        Label fuelCapacityLeftLabel = new Label("FUEL CAPACITY");
        Label fuelCapacityRightLabel = new Label();
        fuelCapacityRightLabel.textProperty().bind(Bindings.format("%d", ship.fuelCapacityProperty()));

        Label healthLeftLabel = new Label("HEALTH");
        Label healthRightLabel = new Label();
        healthRightLabel.textProperty().bind(Bindings.format("%d", ship.healthProperty()));

        Label inventoryLeftLabel = new Label("INVENTORY");
        Label inventoryRightLabel = new Label();
        String inventoryRightLabelText = "";
        for (Item item: ship.getItemInventory().keySet()){
            InventoryEntry entry = ship.getItemInventory().get(item);
            inventoryRightLabelText += "" + item  + " (" + entry.getQuantity() + ")  ";
        }
        inventoryRightLabel.textProperty().bind(Bindings.format("%s", inventoryRightLabelText));

        Label refuelCostLabel = new Label("Refueling costs " + ship.getRefuelCost() + " credits.");
        Button refuelButton = new Button("Refuel");
        refuelButton.setSkin(new ButtonScaleHover(refuelButton));
        refuelButton.setOnAction(event -> {
            try {
                player.refuelShip();
            } catch (Exception e) {
                refuelCostLabel.setText(e.getMessage());
            }
        });
        HBox refuelWrapper = new HBox(20);
        refuelWrapper.getChildren().addAll(refuelCostLabel, refuelButton);

//        Label repairCostLabel = new Label("Repair costs " + ship.getRepairCost() + " credits.");
//        Button repairButton = new Button("Repair");
//        repairButton.setSkin(new ButtonScaleHover(repairButton));
//        repairButton.setOnAction(event -> {
//            try {
//                player.repairShip();
//            } catch (Exception e) {
//                repairCostLabel.setText(e.getMessage());
//            }
//        });
//        HBox repairWrapper = new HBox(20);
//        repairWrapper.getChildren().addAll(repairCostLabel, repairButton);

        contentGridPane.addColumn(0, difficultyLeftLabel, nameLeftLabel,
                cargoCapacityLeftLabel, totalItemsLeftLabel, inventoryLeftLabel,
                fuelLeftLabel, fuelCapacityLeftLabel, null, healthLeftLabel);

        contentGridPane.addColumn(1, difficultyRightLabel, nameRightLabel,
                cargoCapacityRightLabel, totalItemsRightLabel,
                inventoryRightLabel, fuelRightLabel, fuelCapacityRightLabel,
                refuelWrapper, healthRightLabel);
        return contentGridPane;
    }
}
