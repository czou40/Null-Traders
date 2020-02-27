
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Vector;

public class RegionCharacteristicsScreen extends GameScreen {
    private Region displayedRegion;


    public RegionCharacteristicsScreen(Stage primaryStage, Game game, Region region) {
        super(primaryStage, game, "Characteristics for " + region.getName(), true);
        this.displayedRegion = region;
    }

    @Override //Test
    public Pane constructContentPane() {
        Label nameRightLabel= new Label();
        Label descriptionRightLabel= new Label();
        Label techLevelRightLabel= new Label();
        //Vector<Label> itemsRightLabels = new Vector<>();
        Label itemsRightLabel = new Label();


        MyGridPane contentGridPane = new MyGridPane(new double[]{10, 10, 10, 10, 10, 10},
                new double[]{30, 70});

        Label nameLeftLabel = new Label("NAME");
        Label descriptionLeftLabel = new Label("DESCRIPTION");
        Label techLevelLeftLabel = new Label("TECHNOLOGY LEVEL");
        Label itemsLeftLabel = new Label("ITEMS SOLD");
        if(!displayedRegion.isFound()){
            nameRightLabel.setText("???");
            descriptionRightLabel.setText("???");
            techLevelRightLabel.setText("???");
            itemsRightLabel.setText("???");
            //itemsRightLabels.add(new Label("???"));
        } else {
            nameRightLabel.setText(displayedRegion.getName());
            descriptionRightLabel.setText(displayedRegion.getDescription());
            techLevelRightLabel.setText("" + displayedRegion.getTechnologyLevel());
            String itemString = "";
            for (Item item : displayedRegion.getMarketplace().getStock().keySet()) {
                itemString += item.getName() + ", ";
                //itemsRightLabels.add(new Label(item.getName() + ","));
            }
            itemString = itemString.substring(0, itemString.length() - 2);
            itemsRightLabel.setText(itemString);
        }

        contentGridPane.addColumn(0, nameLeftLabel, descriptionLeftLabel,
                techLevelLeftLabel, itemsLeftLabel);
        contentGridPane.addColumn(1, nameRightLabel, descriptionRightLabel,
                techLevelRightLabel, itemsRightLabel);
        /*
        for (int i = 0; i < itemsRightLabels.size(); i++) {
            contentGridPane.add(itemsRightLabels.get(i), 1, i + 3);
        }
         */

        return contentGridPane;

    }
}