import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class Screen {
    private Stage primaryStage;

    public Screen(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void display() {
        primaryStage.setScene(constructScene());
        primaryStage.show();
    }

    public abstract Scene constructScene();
}
