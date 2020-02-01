import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class Screen {
    private Stage primaryStage;
    protected Game game;    //easy access in child class

    public Screen(Stage primaryStage, Game game) {
        this.primaryStage = primaryStage;
        this.game = game;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Game getGame() {
        return game;
    }

    public void display() {
        primaryStage.setScene(constructScene());
        primaryStage.show();
    }

    public abstract Scene constructScene();
}
