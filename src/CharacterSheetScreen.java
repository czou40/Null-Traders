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
//<<<<<<< HEAD
        MyProgressBar fighterPointBar = new MyProgressBar(player.getFighter(), startingSkillPoints);
        fighterPointBar.pointProperty().bind(player.fighterProperty());
        fighterPointBar.setMaxWidth(9999);

        Label pilotLabel = new Label("PILOT");
        MyProgressBar pilotPointBar = new MyProgressBar(player.getPilot(), startingSkillPoints);
        pilotPointBar.pointProperty().bind(player.pilotProperty());
//=======
//        MyProgressBar fighterPointBar = new MyProgressBar(player.getFighter() + player.getUpgrades()[1].getUpgradeLvl(), startingSkillPoints);
//        fighterPointBar.setMaxWidth(9999);
//
//        Label pilotLabel = new Label("PILOT");
//        MyProgressBar pilotPointBar = new MyProgressBar(player.getPilot() + player.getUpgrades()[0].getUpgradeLvl(), startingSkillPoints);
//>>>>>>> 568ef872fa2eaff4d1025ef4d9e2531a500e758b
        pilotPointBar.setMaxWidth(9999);

        Label merchantLabel = new Label("MERCHANT");
        MyProgressBar merchantPointBar = new MyProgressBar(player.getMerchant() + player.getUpgrades()[2].getUpgradeLvl(),
                startingSkillPoints);
        merchantPointBar.pointProperty().bind(player.merchantProperty());
        merchantPointBar.setMaxWidth(9999);

        Label engineerLabel = new Label("ENGINEER");
        MyProgressBar engineerPointBar = new MyProgressBar(player.getEngineer() + player.getUpgrades()[3].getUpgradeLvl(),
                startingSkillPoints);
        engineerPointBar.pointProperty().bind(player.engineerProperty());
        engineerPointBar.setMaxWidth(9999);

        //UPGRADE STUFF
        Label pilLabel = new Label("Pilot Upgrade:");
        Label pilVal;
        if(player.getUpgrades()[0] == player.emptySlot) {
            pilVal = new Label("[None Installed]");
        } else {
            pilVal = new Label(player.getUpgrades()[0].toString());
        }

        Label figLabel = new Label("Fighter Upgrade:");
        Label figVal;
        if(player.getUpgrades()[0] == player.emptySlot) {
            figVal = new Label("[None Installed]");
        } else {
            figVal = new Label(player.getUpgrades()[1].toString());
        }

        Label merLabel = new Label("Merchant Upgrade:");
        Label merVal;
        if(player.getUpgrades()[0] == player.emptySlot) {
            merVal = new Label("[None Installed]");
        } else {
            merVal = new Label(player.getUpgrades()[2].toString());
        }

        Label engLabel = new Label("Engineering Upgrade:");
        Label engVal;
        if(player.getUpgrades()[0] == player.emptySlot) {
            engVal = new Label("[None Installed]");
        } else {
            engVal = new Label(player.getUpgrades()[3].toString());
        }

        contentGridPane.addColumn(0, difficultyLeftLabel, creditLeftLabel,
                pilotLabel, fighterLabel, merchantLabel, engineerLabel, pilLabel, figLabel, merLabel, engLabel);

        contentGridPane.addColumn(1, difficultyRightLabel, creditRightLabel,
                pilotPointBar.withLabel(), fighterPointBar.withLabel(),
                merchantPointBar.withLabel(), engineerPointBar.withLabel(), pilVal, figVal, merVal, engVal);
        return contentGridPane;
    }
}