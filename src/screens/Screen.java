package screens;

import cores.Game;
import cores.Main;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class describes a screen.
 */
public abstract class Screen {
    private Stage primaryStage;
    protected Game game;    //easy access in child class
    private String musicPath;
    private Pane root;


    
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
        root = constructRoot();
        if (primaryStage.getScene() == null) {
            primaryStage.setScene(new Scene(root));
        } else {
            primaryStage.getScene().setRoot(root);
        }
        primaryStage.show();
        doAfterScreenIsShown();
    }
    public void addToRoot(Node node) {
        root.getChildren().add(node);
    }

    public ReadOnlyDoubleProperty getRootWidth() {
        return root.widthProperty();
    }

    public ReadOnlyDoubleProperty getRootHeight() {
        return root.heightProperty();
    }

    public abstract Pane constructRoot();

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
