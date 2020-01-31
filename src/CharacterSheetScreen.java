import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CharacterSheetScreen extends Screen {
    private Game game;

    public CharacterSheetScreen(Stage primaryStage, Game game) {
        super(primaryStage);
        this.game = game;
    }

    @Override
    public Scene constructScene() {
        Player player = game.getPlayer();
        Label nameLabel = new Label(player.getName());
        nameLabel.setId("h1");
        ImageView characterImageView = new ImageView(new Image("file:src/images/test.gif"));


        Label fighterLabel = new Label(player.getFighter() + "/" + game.getDifficulty().getStartingSkillPoints());
        MyProgressBar fighterPointBar = new MyProgressBar(player.getFighter(), game.getDifficulty().getStartingSkillPoints());
        MyProgressBar pilotPointBar = new MyProgressBar(player.getPilot(), game.getDifficulty().getStartingSkillPoints());
        MyProgressBar merchantPointBar = new MyProgressBar(player.getMerchant(), game.getDifficulty().getStartingSkillPoints());
        MyProgressBar engineerPointBar = new MyProgressBar(player.getEngineer(), game.getDifficulty().getStartingSkillPoints());


        double[] rowConstraints = {10, 65, 15, 0};
        double[] columnConstraints = {100};
        MyGridPane root = new MyGridPane(rowConstraints,columnConstraints);
        root.addColumn(0,nameLabel,characterImageView,pilotPointBar);
        Scene characterSheetScene = new Scene(root, 800, 600);
        characterSheetScene.getStylesheets().addAll("styles/general.css","styles/character-sheet-screen.css");
        return characterSheetScene;
    }
}
