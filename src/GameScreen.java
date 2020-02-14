import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public abstract class GameScreen extends Screen {
    private static Media bkg;
    static MediaPlayer bkgPlayer;
    private static boolean hasStarted;

    private String title;
    private boolean requiresSideBar;
    private Pane contentPane;
    public GameScreen(Stage primaryStage, Game game, String title, boolean requiresSideBar) {
        super(primaryStage, game);
        this.title = title.toUpperCase();
        this.requiresSideBar = requiresSideBar;

        //music
        bkg = new Media(new File("out/production/cs2340/sounds/game.mp3").toURI().toString());
        bkgPlayer = new MediaPlayer(bkg);
        bkgPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public ReadOnlyDoubleProperty getContentWidth() {
        return contentPane.widthProperty();
    }

    public ReadOnlyDoubleProperty getContentHeight() {
        return contentPane.heightProperty();
    }

    @Override
    public Scene constructScene() {
        //music stuff
        if(!hasStarted) {
            bkgPlayer.play();
            WelcomeScreen.themePlayer.stop();
            hasStarted = true;
        }

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("h1");
        MyGridPane titlePane = new MyGridPane();
        titlePane.add(titleLabel, 0, 0);
        titlePane.getStyleClass().addAll("transparent-pane-dark");
        contentPane = constructContentPane();
        contentPane.getStyleClass().addAll("transparent-pane-medium");
        MyGridPane wrapperPane;
        double[] rootRowConstraints = {5, 10, 80, 5};
        double[] rootColumnConstraints;
        if (requiresSideBar) {
            Pane sideBarPane = constructSideBarPane();
            wrapperPane = new MyGridPane(new double[]{100}, new double[]{20, 80});
            wrapperPane.addRow(0, sideBarPane, contentPane);
            rootColumnConstraints = new double[]{3, 94, 3};
        } else {
            wrapperPane = new MyGridPane();
            wrapperPane.addRow(0, contentPane);
            rootColumnConstraints = new double[]{20, 60, 20};
        }
        MyGridPane root = new MyGridPane(rootRowConstraints, rootColumnConstraints);
        root.addColumn(1, null, titlePane, wrapperPane, null);
        Scene scene = new Scene(root, 1780, 720);
        scene.getStylesheets().addAll("styles/general.css",
                "styles/blurry-background.css");
        return scene;
    }

    public abstract Pane constructContentPane();

    private MyGridPane constructSideBarPane() {
        ImageView characterImageView = new ImageView(new Image("file:src/images/test.gif"));
        characterImageView.setPreserveRatio(true);
        Player player = game.getPlayer();
        Label nameLabel = new Label(player.getName());
        nameLabel.getStyleClass().add("h2");
        MyGridPane sideBarPane = new MyGridPane(new double[]{10, 10, 10, 10, 10},
                new double[]{100});

        //characterImageView.fitHeightProperty().bind(getPrimaryStage().heightProperty().multiply(
        // 0.1));
        sideBarPane.addColumn(0,
                new MyNavigationButton("Buy Cargo", null),
                new MyNavigationButton("Sell Cargo", null),
                new MyNavigationButton("Player Profile",
                        new CharacterSheetScreen(getPrimaryStage(), game)),
                new MyNavigationButton("Map", new MapScreen(getPrimaryStage(), game)));
        sideBarPane.getStyleClass().addAll("transparent-pane-light");
        return sideBarPane;
    }

    public void addToContentPane(Node node) {
        contentPane.getChildren().add(node);
    }
}