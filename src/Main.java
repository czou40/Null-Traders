import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private Game game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Game();
        primaryStage.setTitle("Welcome to Null Traders!");
        (new WelcomeScreen(primaryStage, game)).display();
        //new MapScreen(primaryStage, game).display();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
