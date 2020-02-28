import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CharacterSheetScreen extends GameScreen {

    public CharacterSheetScreen(Stage primaryStage, Game game) {
        super(primaryStage, game, "Summary of " + game.getPlayer().getName(), true);

        //TEST
        /*
        for (Region r : game.getUniverse().getRegions()) {
            r.getMarketplace().printStock();
        }
         */
    }

    @Override //Test
    public Pane constructContentPane() {
        Player player = game.getPlayer();
        MyGridPane contentGridPane = new MyGridPane(new double[] {10, 10, 10, 10, 10, 10},
                new double[] {30, 70});

        Label difficultyLeftLabel = new Label("DIFFICULTY");
        Label difficultyRightLabel = new Label(game.getDifficulty().toString());
        //difficultyLabel.getStyleClass().add("h2");

        Label creditLeftLabel = new Label("CREDIT");
        Label creditRightLabel = new Label();
        creditRightLabel.textProperty().bind(Bindings.format("%s", player.creditsProperty()));
        //creditLabel.getStyleClass().add("h2");

        int startingSkillPoints = game.getDifficulty().getStartingSkillPoints();
        Label fighterLabel = new Label("FIGHTER");
        MyProgressBar fighterPointBar = new MyProgressBar(player.getFighter(), startingSkillPoints);
        fighterPointBar.setMaxWidth(9999);

        Label pilotLabel = new Label("PILOT");
        MyProgressBar pilotPointBar = new MyProgressBar(player.getPilot(), startingSkillPoints);
        pilotPointBar.setMaxWidth(9999);

        Label merchantLabel = new Label("MERCHANT");
        MyProgressBar merchantPointBar = new MyProgressBar(player.getMerchant(),
                startingSkillPoints);
        merchantPointBar.setMaxWidth(9999);

        Label engineerLabel = new Label("ENGINEER");
        MyProgressBar engineerPointBar = new MyProgressBar(player.getEngineer(),
                startingSkillPoints);
        engineerPointBar.setMaxWidth(9999);

        contentGridPane.addColumn(0, difficultyLeftLabel, creditLeftLabel,
                pilotLabel, fighterLabel, merchantLabel, engineerLabel);

        contentGridPane.addColumn(1, difficultyRightLabel, creditRightLabel,
                pilotPointBar.withLabel(), fighterPointBar.withLabel(),
                merchantPointBar.withLabel(), engineerPointBar.withLabel());
        return contentGridPane;
    }
}