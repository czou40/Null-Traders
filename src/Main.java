import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Welcome to Null Traders!");
        WelcomeScreen welcomeScreen = new WelcomeScreen(primaryStage);
        welcomeScreen.display();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
