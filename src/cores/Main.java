package cores;

import cores.utilities.NameGenerator;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import screens.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    private Game game;
    private static String musicPathname;
    private static MediaPlayer musicPlayer;
    private static boolean hasStartedMusic;

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



    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Game();
        primaryStage.setTitle("Welcome to Null Traders!");
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
        primaryStage.setMaximized(true);
        new WelcomeScreen(primaryStage, game).display();
        //new classes.screens.MapScreen(primaryStage, game).display();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
