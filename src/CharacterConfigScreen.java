import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CharacterConfigScreen extends Screen {
    private static final boolean DEBUG = true; //only set true if testing

    private Game game;
    private Player player;
    private Stage primaryStage;

    private Label title;
    private TextField nameField;

    private ToggleGroup difficultyToggleGroup;
    private RadioButton cadetToggle;
    private RadioButton captainToggle;
    private RadioButton admiralToggle;

    private HBox pointsAvailableWrapper;
    private Label pointsAvailableNumber;
    private Label pointsAvailableLabel;

    private MyGridPane slidersWrapper;

    private Label pilotSliderLabel;
    private Slider pilotSlider;

    private Label fighterSliderLabel;
    private Slider fighterSlider;

    private Label merchantSliderLabel;
    private Slider merchantSlider;

    private Label engineerSliderLabel;
    private Slider engineerSlider;

    private Button submitButton;

    private Label errorMessage; //displays error to the user

    private final Slider[] sliders;

    public CharacterConfigScreen(Stage primaryStage) {
        super(primaryStage);
        game = new Game();
        player = game.getPlayer();

        title = new Label("This is the character config screen!");
        nameField = new TextField();
        nameField.setPromptText("Enter a name");

        difficultyToggleGroup = new ToggleGroup();
        cadetToggle = new RadioButton("Cadet");
        captainToggle = new RadioButton("Captain");
        admiralToggle = new RadioButton("Admiral");
        difficultyToggleGroup.getToggles().addAll(cadetToggle, captainToggle, admiralToggle);

        pointsAvailableLabel = new Label("Skill Points Remaining: ");
        pointsAvailableNumber = new Label("30");  //will get filled in through property binding
        pointsAvailableWrapper = new HBox(pointsAvailableLabel, pointsAvailableNumber);

        pilotSliderLabel = new Label("Pilot: ");
        fighterSliderLabel = new Label("Fighter: ");
        merchantSliderLabel = new Label("Merchant: ");
        engineerSliderLabel = new Label("Engineer: ");

        pilotSlider = new Slider(0, game.getDifficulty().getStartingSkillPoints(), 0);
        fighterSlider = new Slider(0, game.getDifficulty().getStartingSkillPoints(), 0);
        merchantSlider = new Slider(0, game.getDifficulty().getStartingSkillPoints(), 0);
        engineerSlider = new Slider(0, game.getDifficulty().getStartingSkillPoints(), 0);


        slidersWrapper = new MyGridPane();
        slidersWrapper.addColumn(0, pilotSliderLabel, fighterSliderLabel, merchantSliderLabel, engineerSliderLabel);
        slidersWrapper.addColumn(1, pilotSlider, fighterSlider, merchantSlider, engineerSlider);
        sliders = new Slider[] {pilotSlider, fighterSlider, merchantSlider, engineerSlider};
        for (Slider s : sliders) {
            //labels every 5 points, points increment by 1

            s.setMajorTickUnit(5.0);
            s.setMinorTickCount(5);
            s.setSnapToTicks(true);
            s.setShowTickLabels(true);
            s.setShowTickMarks(true);
        }

        submitButton = new Button("Submit Character");
        submitButton.setOnAction(e -> {
           submitCharacter();
        });

        errorMessage = new Label("");
        initialize();
    }

    @Override
    public Scene constructScene() {
        VBox difficultyToggleWrapper = new VBox(cadetToggle, captainToggle, admiralToggle);
        HBox pointsAvailableWrapper = new HBox(pointsAvailableLabel, pointsAvailableNumber);
        FlowPane root = new FlowPane(
                title, nameField, difficultyToggleWrapper, pointsAvailableWrapper, slidersWrapper, submitButton, errorMessage);
        return new Scene(root,800,600);
    }



    public void initialize() {
        player = game.getPlayer();

        //set default toggle and update difficulty based on model
        difficultyToggleGroup.selectToggle(cadetToggle);
        difficultyToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            changeDifficulty(newValue);
        });

        //property binding
        game.difficultyProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Difficulty Changed");
           pointsAvailableNumber.setText(newValue.getStartingSkillPoints().toString());

            for (Slider s : sliders) {
                //bind max value of slider to starting points
                s.setMax(newValue.getStartingSkillPoints());
            }
        });

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
        if (DEBUG) {
            System.out.println("Player name: " + player.getName());
            System.out.println("Sum of points: " + game.getPlayer().sumOfPoints());
            System.out.println("Starting Points: " + game.getDifficulty().getStartingSkillPoints());
        }
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

        CharacterSheetScreen nextScreen = new CharacterSheetScreen(getPrimaryStage(), game);
        nextScreen.display();

        /*
        if we use FXML
        primaryStage.setScene(FXMLLoader.load(some syntaxy stuff), width, height))
         */

    }
}