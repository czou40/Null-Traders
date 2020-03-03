package screens;

import cores.Game;
import cores.Main;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class describes a screen.
 */
public abstract class Screen {
    private Stage primaryStage;
    protected Game game;    //easy access in child class
    private String musicPath;


    
    /**
     * Constructs a new instance.
     *
     * @param      primaryStage  The primary stage
     * @param      game          The game
     */
    public Screen(Stage primaryStage, Game game) {
        super();
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
        if (primaryStage.getScene() == null) {
            primaryStage.setScene(new Scene(constructRoot()));
        } else {
            primaryStage.getScene().setRoot(constructRoot());
        }
        primaryStage.show();
        doAfterScreenIsShown();
    }


    public abstract Parent constructRoot();

    public void doAfterScreenIsShown() {
        if (musicPath != null) {
            Main.setMusic(musicPath);
        }
        /*
        Sometimes, you need to adjust UI after the screen is shown.
        That is because you want to size the nodes correctly.
        Before the scene is shown, you don't know the size of the content pane.
        So you cannot determine the size of the nodes.
         */
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }
}
