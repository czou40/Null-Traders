
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegionCharacteristicsScreen extends GameScreen {
    private Region displayedRegion;


    public RegionCharacteristicsScreen(Stage primaryStage, Game game, Region region) {
        super(primaryStage, game, "Characteristics", true);
        this.displayedRegion = region;
    }

    @Override //Test
    public Pane constructContentPane() {
        Label nameRightLabel= new Label();
        Label descriptionRightLabel= new Label();
        Label techLevelRightLabel= new Label();


        MyGridPane contentGridPane = new MyGridPane(new double[]{10, 10, 10, 10, 10, 10},
                new double[]{30, 70});

        Label nameLeftLabel = new Label("NAME");
        Label descriptionLeftLabel = new Label("DESCRIPTION");
        Label techLevelLeftLabel = new Label("TECHNOLOGY LEVEL");
        if(!displayedRegion.isFound()){
            nameRightLabel.setText("???");
            descriptionRightLabel.setText("???");
            techLevelRightLabel.setText("???");
        } else {
            nameRightLabel.setText(displayedRegion.getName());
            descriptionRightLabel.setText(displayedRegion.getDescription());
            techLevelRightLabel.setText("" + displayedRegion.getTechnologyLevel());
        }

        contentGridPane.addColumn(0, nameLeftLabel, descriptionLeftLabel,
                techLevelLeftLabel);
        contentGridPane.addColumn(1, nameRightLabel, descriptionRightLabel,
                techLevelRightLabel);

        return contentGridPane;

    }
}