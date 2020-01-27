import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Screen {
    private Scene scene;
    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Screen(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public final Scene getScene() {
        if (scene == null) {
            throw new SceneNotInitialized("Must create scene before accessing it.");
        } else {
            return scene;
        }
    }

    public final void setScene(Parent root, double width, double height) {
        scene = new Scene(root, width, height);
    }

    public final void setScene(Parent root) {
        setScene(root, primaryStage.getWidth(), primaryStage.getHeight());
    }
}
