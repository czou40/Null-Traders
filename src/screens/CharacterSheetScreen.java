package screens;

import uicomponents.*;
import cores.characters.Player;
import cores.Game;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CharacterSheetScreen extends GameScreen {

    public CharacterSheetScreen(Stage primaryStage, Game game) {
        super(primaryStage, game, "Summary of " + game.getPlayer().getName(), true);

        //TEST
        /*
        for (cores.Region r : game.getUniverse().getRegions()) {
            r.getMarketplace().printStock();
        }
         */
    }

    @Override //Test
    public Pane constructContentPane() {
        Player player = game.getPlayer();
        MyGridPane contentGridPane
                = new MyGridPane(null, new double[] {30, 70});

        Label difficultyLeftLabel = new Label("DIFFICULTY");
        Label difficultyRightLabel = new Label(game.getDifficulty().toString());

        Label creditLeftLabel = new Label("CREDIT");
        Label creditRightLabel = new Label();
        creditRightLabel.textProperty().bind(Bindings.format("%s", player.creditsProperty()));

        int startingSkillPoints = game.getDifficulty().getStartingSkillPoints();
        Label fighterLabel = new Label("FIGHTER");
        MyProgressBar fighterPointBar = new MyProgressBar(0, startingSkillPoints);
        fighterPointBar.pointProperty().bind(player.skillProperty(Player.SkillType.FIG));
        fighterPointBar.setMaxWidth(9999);

        Label pilotLabel = new Label("PILOT");
        MyProgressBar pilotPointBar = new MyProgressBar(0, startingSkillPoints);
        pilotPointBar.pointProperty().bind(player.skillProperty(Player.SkillType.PIL));
        pilotPointBar.setMaxWidth(9999);

        Label merchantLabel = new Label("MERCHANT");
        MyProgressBar merchantPointBar = new MyProgressBar(0, startingSkillPoints);
        merchantPointBar.pointProperty().bind(player.skillProperty(Player.SkillType.MER));
        merchantPointBar.setMaxWidth(9999);

        Label engineerLabel = new Label("ENGINEER");
        MyProgressBar engineerPointBar = new MyProgressBar(0, startingSkillPoints);
        engineerPointBar.pointProperty().bind(player.skillProperty(Player.SkillType.ENG));
        engineerPointBar.setMaxWidth(9999);

        //UPGRADE STUFF
        Label pilLabel = new Label("PILOT UPGRADE");
        Label pilVal = new UpgradeLabel(Player.SkillType.PIL);

        Label figLabel = new Label("FIGHTER UPGRADE");
        Label figVal = new UpgradeLabel(Player.SkillType.FIG);

        Label merLabel = new Label("MERCHANT UPGRADE");
        Label merVal = new UpgradeLabel(Player.SkillType.MER);

        Label engLabel = new Label("ENGINEERING UPGRADE");
        Label engVal = new UpgradeLabel(Player.SkillType.ENG);

        contentGridPane.addColumn(0, difficultyLeftLabel, creditLeftLabel,
                pilotLabel, fighterLabel, merchantLabel, engineerLabel, pilLabel,
                figLabel, merLabel, engLabel);

        contentGridPane.addColumn(1, difficultyRightLabel, creditRightLabel,
                pilotPointBar.withLabel(), fighterPointBar.withLabel(),
                merchantPointBar.withLabel(), engineerPointBar.withLabel(), pilVal,
                figVal, merVal, engVal);
        return contentGridPane;
    }

    private class UpgradeLabel extends Label {
        Player.SkillType type;
        private UpgradeLabel(Player.SkillType type) {
            super();
            this.type = type;
            updateText();
            game.getPlayer().getUpgradeProperty(type).addListener(e -> {
                updateText();
            });
        }

        private void updateText() {
            if (game.getPlayer().getUpgrade(type) == null) {
                setText("[None Installed]");
            } else {
                setText(game.getPlayer().getUpgrade(type).toString());
            }
        }
    };
}