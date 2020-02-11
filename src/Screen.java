import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class describes a screen.
 */
public abstract class Screen {
    private Stage primaryStage;
    protected Game game;    //easy access in child class

    
    /**
     * Constructs a new instance.
     *
     * @param      primaryStage  The primary stage
     * @param      game          The game
     */
    public Screen(Stage primaryStage, Game game) {
        this.primaryStage = primaryStage;
        this.game = game;
    }

    /**
     * Gets the primary stage.
     *
     * @return     The primary stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Gets the game.
     *
     * @return     The game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Displays the object.
     */
    public void display() {
        primaryStage.setScene(constructScene());
        primaryStage.show();
        after();
    }

    /**
     * { function_description }
     *
     * @return     { description_of_the_return_value }
     */
    public abstract Scene constructScene();

    public void after() {
    }
}
