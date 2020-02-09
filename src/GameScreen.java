import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class GameScreen extends Screen {
    private String title;
    private boolean requiresSideBar;
    public GameScreen(Stage primaryStage, Game game, String title, boolean requiresSideBar) {
        super(primaryStage, game);
        this.title = title.toUpperCase();
        this.requiresSideBar = requiresSideBar;
    }

    @Override
    public Scene constructScene() {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("h1");
        MyGridPane titlePane = new MyGridPane();
        titlePane.add(titleLabel, 0, 0);
        titlePane.getStyleClass().addAll("transparent-pane-dark");
        Pane contentPane = constructContentPane();
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
        Scene characterSheetScene = new Scene(root, 1280, 720);
        characterSheetScene.getStylesheets().addAll("styles/general.css",
                "styles/blurry-background.css");
        return characterSheetScene;
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
}
