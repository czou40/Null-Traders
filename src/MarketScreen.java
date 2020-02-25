import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MarketScreen extends GameScreen {
    private Game game;
    private Marketplace marketplace;
    private Map<Item, StockEntry> stock;
    private ScrollPane scrollPane;

    public MarketScreen(Stage primaryStage, Game game) {
        super(primaryStage, game, game.getPlayer().getCurrentRegion().getMarketplace().getName(), true);
        this.marketplace = game.getPlayer().getCurrentRegion().getMarketplace();
        this.stock = marketplace.getStock();
    }

    @Override
    public Pane constructContentPane() {
        MyGridPane itemsPane = new MyGridPane(MyGridPane.getSpan(10), MyGridPane.getSpan(1));
        for (Map.Entry<Item, StockEntry> i : stock.entrySet()) {
            itemsPane.addColumn(0, new BuyItemBox(i));
        }
        scrollPane = new ScrollPane(itemsPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        MyGridPane contentPane = new MyGridPane();
        contentPane.add(scrollPane, 0, 0);
        return contentPane;
    }

    @Override
    public void adjustUIAfterScreenIsShown() {
        scrollPane.prefWidthProperty().bind(getContentWidth());
    }

}
