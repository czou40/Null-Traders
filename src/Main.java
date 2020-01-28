import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        /*
        Button start = new Button("Start Game");

        start.setOnAction(e -> {
            WelcomeScreen screen = new WelcomeScreen();
            primaryStage.close();
            screen.show();
        });
        //Parent root = FXMLLoader.load(getClass().getResource("static.fxml"));
        StackPane uiLoader = new StackPane();
        uiLoader.getChildren().add(start);
        */




        primaryStage.setTitle("Welcome to Null Traders!");
        primaryStage.setScene((new WelcomeScreen()).getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
