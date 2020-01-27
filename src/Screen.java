import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public abstract class Screen {
    private Scene scene;

    public Screen() {
        //scene should be set in subclass
    }

    public final Scene getScene() throws SceneNotInitialized {
        if (scene == null) {
            throw new SceneNotInitialized("Must create scene before accessing it.");
        } else {
            return scene;
        }
    }

    public final void setScene(Parent root, int width, int height) {
        scene = new Scene(root, width, height);
    }
}
