import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CharacterConfigScreen extends GameScreen {
    private static final boolean DEBUG = false; //only set true if testing

    private Player player;

    private Label title;
    private TextField nameField;

    private Label difficultyLabel;
    private ToggleGroup difficultyToggleGroup;
    private MyGridPane difficultyToggleWrapper;
    private RadioButton cadetToggle;
    private RadioButton captainToggle;
    private RadioButton admiralToggle;

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

    private Slider[] sliders;

    public CharacterConfigScreen(Stage primaryStage, Game game) {
        super(primaryStage, game, "New Game", false);
        player = game.getPlayer();
    }

    @Override
    public Pane constructContentPane() {
        initializeUI();
        initialize();
        difficultyToggleWrapper = new MyGridPane(new double[]{100}, MyGridPane.getSpan(3),
                HPos.LEFT, VPos.CENTER);
        difficultyToggleWrapper.addRow(0, cadetToggle, captainToggle, admiralToggle);
        //FlowPane root = new FlowPane(
        //       title, nameField, difficultyToggleWrapper,
        //       pointsAvailableWrapper, slidersWrapper, submitButton,
        //       errorMessage);
        MyGridPane content = new MyGridPane(MyGridPane.getSpan(10), new double[]{25, 75});
        content.addColumn(0, title, difficultyLabel, pointsAvailableLabel, pilotSliderLabel,
                fighterSliderLabel, merchantSliderLabel, engineerSliderLabel);
        content.addColumn(1, nameField, difficultyToggleWrapper, pointsAvailableNumber,
                pilotSlider, fighterSlider, merchantSlider,
                engineerSlider, submitButton, errorMessage);
        return content;
    }

    private void initializeUI() {
        title = new Label("NAME");
        nameField = new TextField();
        nameField.setPromptText("Enter a Name");
        difficultyLabel = new Label("DIFFICULTY");
        difficultyToggleGroup = new ToggleGroup();
        cadetToggle = new RadioButton("CADET");
        captainToggle = new RadioButton("CAPTAIN");
        admiralToggle = new RadioButton("ADMIRAL");
        difficultyToggleGroup.getToggles().addAll(cadetToggle, captainToggle, admiralToggle);

        pointsAvailableLabel = new Label("SKILL POINTS: ");
        pointsAvailableNumber = new Label("30");  //will get filled in through property binding

        pilotSliderLabel = new Label("PILOT");
        fighterSliderLabel = new Label("FIGHTER");
        merchantSliderLabel = new Label("MERCHANT");
        engineerSliderLabel = new Label("ENGINEER");

        pilotSlider = new Slider(0, game.getDifficulty().getStartingSkillPoints(), 0);
        fighterSlider = new Slider(0, game.getDifficulty().getStartingSkillPoints(), 0);
        merchantSlider = new Slider(0, game.getDifficulty().getStartingSkillPoints(), 0);
        engineerSlider = new Slider(0, game.getDifficulty().getStartingSkillPoints(), 0);


        slidersWrapper = new MyGridPane();
        slidersWrapper.addColumn(0, pilotSliderLabel, fighterSliderLabel,
                merchantSliderLabel, engineerSliderLabel);
        slidersWrapper.addColumn(1, pilotSlider, fighterSlider, merchantSlider, engineerSlider);
        sliders = new Slider[] {pilotSlider, fighterSlider, merchantSlider, engineerSlider};
        for (Slider s : sliders) {
            //labels every 5 points, points increment by 1

            s.setMajorTickUnit(5.0);
            s.setMinorTickCount(4);
            s.setSnapToTicks(true);
            s.setShowTickLabels(true);
            s.setShowTickMarks(true);
        }

        submitButton = new Button("Submit Character");
        submitButton.setSkin(new ButtonScaleHover(submitButton)); //effect is applied here

        submitButton.setOnAction(e -> {
            submitCharacter();
        });

        errorMessage = new Label("");
    }

    public void initialize() {
        //set default toggle and update difficulty based on model
        difficultyToggleGroup.selectToggle(cadetToggle);
        difficultyToggleGroup.selectedToggleProperty().addListener((
                observable, oldValue, newValue) -> {
            changeDifficulty(newValue);
        });

        //property binding
        game.difficultyProperty().addListener((observable, oldValue, newValue) -> {
            //pointsAvailableNumber.setText(newValue.getStartingSkillPoints().toString());

            for (Slider s : sliders) {
                //bind max value of slider to starting points
                s.setMax(newValue.getStartingSkillPoints());
            }
            pointsAvailableNumber.textProperty().bind(Bindings.format("%s",
                    newValue.startingSkillPointsProperty().
                            subtract(player.pilotProperty().
                                    add(player.fighterProperty().
                                            add(player.merchantProperty().
                                                    add(player.engineerProperty()))))));
        });

        pointsAvailableNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Integer.parseInt(newValue) < 0) {
                pointsAvailableNumber.setStyle("-fx-text-fill:#f44336;");
            } else {
                pointsAvailableNumber.setStyle("-fx-text-fill:#efefef;");

            }
        });

        //points available decrease as skill points are allocated
        pointsAvailableNumber.textProperty().bind(Bindings.format("%s",
                game.getDifficulty().startingSkillPointsProperty().
                        subtract(player.pilotProperty().
                                add(player.fighterProperty().
                                        add(player.merchantProperty().
                                                add(player.engineerProperty()))))));

        player.nameProperty().bind(nameField.textProperty());

        player.pilotProperty().bind(pilotSlider.valueProperty().add(0.5));
        player.fighterProperty().bind(fighterSlider.valueProperty().add(0.5));
        player.merchantProperty().bind(merchantSlider.valueProperty().add(0.5));
        player.engineerProperty().bind(engineerSlider.valueProperty().add(0.5));
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
            player.setShip(new Ship(game.getDifficulty()));
            moveToCharacterSheetScreen();
        }
    }

    public boolean validateName() {
        if ((nameField.getText() == null) || (nameField.getText().equals(""))) {
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
