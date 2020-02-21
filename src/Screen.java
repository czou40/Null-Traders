import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

/**
 * This class describes a screen.
 */
public abstract class Screen {
    private Stage primaryStage;
    protected Game game;    //easy access in child class
    private static String musicPathname;
    private static MediaPlayer musicPlayer;
    private static boolean hasStartedMusic;

    
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

    public static void setMusic(String pathname) {
        if (!hasStartedMusic) {
            musicPathname = pathname;
            Media music = new Media(new File(pathname).toURI().toString());
            musicPlayer = new MediaPlayer(music);
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            musicPlayer.play();
            hasStartedMusic = true;
        } else {
            if (!pathname.equals(musicPathname)) {
                musicPathname = pathname;
                musicPlayer.stop();
                Media music = new Media(new File(pathname).toURI().toString());
                musicPlayer = new MediaPlayer(music);
                musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                musicPlayer.play();
            }
        }
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
        adjustUIAfterScreenIsShown();
    }


    public abstract Scene constructScene();

    public void adjustUIAfterScreenIsShown() {
        /*
        Sometimes, you need to adjust UI after the screen is shown.
        That is because you want to size the nodes correctly.
        Before the scene is shown, you don't know the size of the content pane.
        So you cannot determine the size of the nodes.
         */
    }
}
