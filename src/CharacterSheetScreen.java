import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CharacterSheetScreen extends Screen {

    public CharacterSheetScreen(Stage primaryStage, Game game) {
        super(primaryStage, game);
    }

    @Override
    public Scene constructScene() {
        Player player = game.getPlayer();
        Label nameLabel = new Label(player.getName());
        nameLabel.getStyleClass().add("h1");

        Label difficultyLabel = new Label("DIFFICULTY: " + game.getDifficulty().toString());
        difficultyLabel.getStyleClass().add("h2");
        Label creditLabel = new Label("CREDIT: " + game.getDifficulty().getCredits());
        creditLabel.getStyleClass().add("h2");
        MyGridPane gameInfoPane = new MyGridPane(MyGridPane.getSpan(1), MyGridPane.getSpan(2));
        gameInfoPane.addRow(0,difficultyLabel,creditLabel);

        ImageView characterImageView = new ImageView(new Image("file:src/images/test.gif"));

        int startingSkillPoints = game.getDifficulty().getStartingSkillPoints();
        Label fighterLabel = new Label("Fighter: " + player.getFighter() + "/" + startingSkillPoints);
        MyProgressBar fighterPointBar = new MyProgressBar(player.getFighter(), startingSkillPoints);
        fighterPointBar.prefWidthProperty().bind(getPrimaryStage().widthProperty().multiply(0.4).subtract(15));
        Label pilotLabel = new Label("Pilot: " +player.getPilot() + "/" + startingSkillPoints);
        MyProgressBar pilotPointBar = new MyProgressBar(player.getPilot(), startingSkillPoints);
        pilotPointBar.prefWidthProperty().bind(getPrimaryStage().widthProperty().multiply(0.4).subtract(15));
        Label merchantLabel = new Label("Merchant: " +player.getMerchant() + "/" + startingSkillPoints);
        MyProgressBar merchantPointBar = new MyProgressBar(player.getMerchant(), startingSkillPoints);
        merchantPointBar.prefWidthProperty().bind(getPrimaryStage().widthProperty().multiply(0.4).subtract(15));
        Label engineerLabel = new Label("Engineer: " +player.getEngineer() + "/" + startingSkillPoints);
        MyProgressBar engineerPointBar = new MyProgressBar(player.getEngineer(), startingSkillPoints);
        engineerPointBar.prefWidthProperty().bind(getPrimaryStage().widthProperty().multiply(0.4).subtract(15));
        double[] pointPaneRowSpan = {10,40,10,40};
        MyGridPane pointPane = new MyGridPane(MyGridPane.getSpan(2),pointPaneRowSpan);
        pointPane.addRow(0, fighterLabel, fighterPointBar, pilotLabel, pilotPointBar);
        pointPane.addRow(1,merchantLabel, merchantPointBar,engineerLabel, engineerPointBar);
        double[] rowConstraints = {10,10, 50, 15, 15};
        double[] columnConstraints = {100};
        MyGridPane root = new MyGridPane(rowConstraints, columnConstraints);
        root.addColumn(0, nameLabel, gameInfoPane,characterImageView, pointPane);
        Scene characterSheetScene = new Scene(root, 800, 600);
        characterSheetScene.getStylesheets().addAll("styles/general.css", "styles/character-sheet-screen.css");
        return characterSheetScene;
    }
}
