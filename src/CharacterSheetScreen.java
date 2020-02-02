import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CharacterSheetScreen extends GameScreen {

    public CharacterSheetScreen(Stage primaryStage, Game game) {
        super(primaryStage, game, "Summary of " + game.getPlayer().getName());
    }

    @Override
    public Scene constructScene() {
        return super.constructScene(false);
    }

    @Override //Test
    public Pane constructContentPane() {
        Player player = game.getPlayer();
        Label difficultyLeftLabel = new Label("DIFFICULTY");
        Label difficultyRightLabel = new Label(game.getDifficulty().toString());
        //difficultyLabel.getStyleClass().add("h2");
        Label creditLeftLabel = new Label("CREDIT");
        Label creditRightLabel = new Label(game.getDifficulty().getCredits().toString());
        //creditLabel.getStyleClass().add("h2");

        int startingSkillPoints = game.getDifficulty().getStartingSkillPoints();
        Label fighterLabel = new Label("FIGHTER: " + player.getFighter() + "/" + startingSkillPoints);
        MyProgressBar fighterPointBar = new MyProgressBar(player.getFighter(), startingSkillPoints);
        fighterPointBar.prefWidthProperty().bind(getPrimaryStage().widthProperty().multiply(0.75).subtract(15));
        Label pilotLabel = new Label("PILOT: " + player.getPilot() + "/" + startingSkillPoints);
        MyProgressBar pilotPointBar = new MyProgressBar(player.getPilot(), startingSkillPoints);
        pilotPointBar.prefWidthProperty().bind(getPrimaryStage().widthProperty().multiply(0.75).subtract(15));
        Label merchantLabel = new Label("MERCHANT: " + player.getMerchant() + "/" + startingSkillPoints);
        MyProgressBar merchantPointBar = new MyProgressBar(player.getMerchant(), startingSkillPoints);
        merchantPointBar.prefWidthProperty().bind(getPrimaryStage().widthProperty().multiply(0.75).subtract(15));
        Label engineerLabel = new Label("ENGINEER: " + player.getEngineer() + "/" + startingSkillPoints);
        MyProgressBar engineerPointBar = new MyProgressBar(player.getEngineer(), startingSkillPoints);
        engineerPointBar.prefWidthProperty().bind(getPrimaryStage().widthProperty().multiply(0.75).subtract(15));

        MyGridPane contentGridPane = new MyGridPane(new double[] {10, 10, 10, 10, 10, 10}, new double[] {30, 70});
        contentGridPane.addColumn(0, difficultyLeftLabel, creditLeftLabel, pilotLabel, fighterLabel, merchantLabel, engineerLabel);
        contentGridPane.addColumn(1, difficultyRightLabel, creditRightLabel, pilotPointBar, fighterPointBar, merchantPointBar, engineerPointBar);
        return contentGridPane;
    }
}
