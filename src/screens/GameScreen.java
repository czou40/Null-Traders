package screens;

import uicomponents.*;
import cores.characters.Player;
import cores.Game;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class GameScreen extends Screen {


    private String title;
    private boolean requiresSideBar;
    private Pane contentPane;
    private MyGridPane root;

    public GameScreen(Stage primaryStage, Game game, String title, boolean requiresSideBar) {
        super(primaryStage, game);
        this.title = title.toUpperCase();
        this.requiresSideBar = requiresSideBar;
        this.setMusicPath("src/sounds/game.mp3");
    }


    public ReadOnlyDoubleProperty getContentWidth() {
        return contentPane.widthProperty();
    }

    public ReadOnlyDoubleProperty getContentHeight() {
        return contentPane.heightProperty();
    }

    @Override
    public Pane constructRoot() {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("h1");
        MyGridPane titlePane = new MyGridPane(HPos.LEFT, VPos.CENTER);
        titlePane.add(titleLabel, 0, 0);
        titlePane.getStyleClass().add("transparent-pane-dark");
        contentPane = constructContentPane();
        contentPane.getStyleClass().add("transparent-pane-medium");
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
        root = new MyGridPane(rootRowConstraints, rootColumnConstraints);
        root.addColumn(1, null, titlePane, wrapperPane, null);
        root.getStylesheets().addAll("styles/general.css",
                "styles/blurry-background.css");
        return root;
    }

    public abstract Pane constructContentPane();

    private MyGridPane constructSideBarPane() {
        ImageView characterImageView = new ImageView(new Image("file:src/images/test.gif"));
        characterImageView.setPreserveRatio(true);
        Player player = game.getPlayer();
        Label nameLabel = new Label(player.getName());
        nameLabel.getStyleClass().add("h2");
        MyGridPane sideBarPane = new MyGridPane(null,
                new double[]{100});

        //characterImageView.fitHeightProperty().bind(getPrimaryStage().heightProperty().multiply(
        // 0.1));
        sideBarPane.addColumn(0,
                new MyNavigationButton(
                        "BUY CARGO", new MarketScreen(getPrimaryStage(), game, true)),
                new MyNavigationButton(
                        "SELL CARGO", new MarketScreen(getPrimaryStage(), game, false)),
                new MyNavigationButton("PROFILE",
                        new CharacterSheetScreen(getPrimaryStage(), game)),
                new MyNavigationButton("MAP", new MapScreen(getPrimaryStage(), game, this)),
                new MyNavigationButton("SHIP", new ShipScreen(getPrimaryStage(), game)));
        sideBarPane.getStyleClass().addAll("transparent-pane-light");
        return sideBarPane;
    }

    public void addToContentPane(Node node) {
        contentPane.getChildren().add(node);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.toUpperCase();
    }
}