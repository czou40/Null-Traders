import javafx.beans.property.IntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class CharacterConfigScreenController {
    private Game game;
    private Player player;
    private Stage primaryStage;

    private TextField nameField;

    private ToggleGroup difficultySetting;
    private RadioButton cadetToggle;
    private RadioButton captainToggle;
    private RadioButton admiralToggle;

    private Label pointsAvailable;

    private Slider merchantSlider;
    private Slider engineerSlider;
    private Slider pilotSlider;
    private Slider fighterSlider;

    private Button submitButton;

    private Label errorMessage;

    public void initialize() {
        player = game.getPlayer();

        difficultySetting.selectToggle(cadetToggle);
        difficultySetting.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            changeDifficulty(newValue);
        });

        //property binding
        pointsAvailable.textProperty().bind(game.getDifficulty().startingSkillPointsProperty().asString());

        player.nameProperty().bind(nameField.textProperty());

        player.pilotProperty().bind(pilotSlider.valueProperty());
        player.fighterProperty().bind(fighterSlider.valueProperty());
        player.merchantProperty().bind(merchantSlider.valueProperty());
        player.engineerProperty().bind(engineerSlider.valueProperty());
    }

    public void changeDifficulty(Toggle selected) {
        if (selected.equals(cadetToggle)) {
            game.setDifficulty(Difficulty.CADET);
        } else if (selected.equals(captainToggle)) {
            game.setDifficulty(Difficulty.CAPTAIN);
        } else {
            game.setDifficulty(Difficulty.ADMIRAL);
        }
    }

    public void submitCharacter() {
        if (validateName() && validatePoints()) {
            unbindPlayerProperties();
            player.setCredits(game.getDifficulty().getCredits());
            moveToCharacterSheetScreen();
        }
    }

    public boolean validateName() {
        if((nameField.getText() == null) || (nameField.getText().equals(""))) {
            errorMessage.setText("Name cannot be empty.");
            return false;
        }

        return true;
    }

    public boolean validatePoints() {
        if ((game.getPlayer().sumOfPoints() > game.getDifficulty().getStartingSkillPoints())) {
            errorMessage.setText("Not enough points available for current point allocation.");
            return false;
        }

        return true;
    }

    public void unbindPlayerProperties() {
        player.nameProperty().unbind();
        player.pilotProperty().unbind();
        player.fighterProperty().unbind();
        player.merchantProperty().unbind();
        player.engineerProperty().unbind();
    }

    public void moveToCharacterSheetScreen() {
        /*
        if we keep using the screen classes:
        CharacterSheetScreen nextScreen = new CharacterSheetScreen(primaryStage);
        nextScreen.display();
         */

        /*
        if we use FXML
        primaryStage.setScene(FXMLLoader.load(some syntaxy stuff), width, height))
         */

    }
}