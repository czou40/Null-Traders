import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private Game game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Game();

        primaryStage.setTitle("Welcome to Null Traders!");
        WelcomeScreen welcomeScreen = new WelcomeScreen(primaryStage, game);
        welcomeScreen.display();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
