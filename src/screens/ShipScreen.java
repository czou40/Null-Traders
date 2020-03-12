package screens;

import cores.Game;
import cores.characters.Player;
import cores.objects.InventoryEntry;
import cores.objects.Item;
import cores.vehicles.Ship;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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


        contentGridPane.addColumn(0, difficultyLeftLabel, nameLeftLabel,
                cargoCapacityLeftLabel, totalItemsLeftLabel, fuelLeftLabel, fuelCapacityLeftLabel, healthLeftLabel,
                inventoryLeftLabel);

        contentGridPane.addColumn(1, difficultyRightLabel, nameRightLabel,
                cargoCapacityRightLabel, totalItemsRightLabel, fuelRightLabel, fuelCapacityRightLabel, healthRightLabel,
                inventoryRightLabel);
        return contentGridPane;
    }
}
