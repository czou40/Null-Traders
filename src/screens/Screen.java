package screens;

import cores.Game;
import cores.Main;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class describes a screen.
 */
public abstract class Screen {
    private Stage primaryStage;
    protected Game game;    //easy access in child class
    private String musicPath;
    private Pane root;
    private static Stage messageStage;


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

    public void displayAsMessageBox() {
        root = constructRoot();
        root.getStylesheets().addAll("styles/general.css", "styles/message-box.css");
        if (messageStage == null) {
            messageStage = new Stage();
            messageStage.initStyle(StageStyle.UTILITY);
            messageStage.initStyle(StageStyle.TRANSPARENT);
            messageStage.setTitle("Message");
            messageStage.setAlwaysOnTop(true);
            messageStage.setScene(new Scene(root));
            messageStage.getScene().setFill(Color.TRANSPARENT);
        } else  {
            messageStage.close();
            messageStage.getScene().setRoot(root);
        }
        messageStage.show();
        doAfterScreenIsShown();
    }


    public void close() {
        root.getScene().getWindow().hide();
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
        if (messageStage != null) {
            double x = primaryStage.getX() + primaryStage.getScene().getX();
            double y = primaryStage.getY() + primaryStage.getScene().getY();
            double height = primaryStage.getScene().getHeight();
            double width = primaryStage.getScene().getWidth();
            messageStage.setX(x);
            messageStage.setY(y + height - messageStage.getHeight());
            messageStage.setWidth(width);
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
