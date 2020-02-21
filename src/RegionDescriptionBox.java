import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RegionDescriptionBox extends VBox {
    private SimpleObjectProperty<Region> region;
    private Label nameLabel;
    private Label descriptionLabel;
    private Label technologyLabel;
    private Label marketplaceLabel;
    public RegionDescriptionBox(SimpleObjectProperty<Region> regionProperty) {
        super();
        this.region = new SimpleObjectProperty<>();
        this.region.bind(regionProperty);
        this.region.addListener(e -> {
            updateLabels();
        });
        this.nameLabel = new Label();
        this.descriptionLabel = new Label();
        this.technologyLabel = new Label();
        this.marketplaceLabel = new Label();
        updateLabels();
        this.getChildren().addAll(nameLabel, descriptionLabel, technologyLabel, marketplaceLabel);
        this.toBack();
    }

    public Region getRegion() {
        return region.get();
    }

    public SimpleObjectProperty<Region> regionProperty() {
        return region;
    }

    public void updateLabels() {
        nameLabel.setText("Name: " + region.get().getName());
        descriptionLabel.setText("Description: " + region.get().getDescription());
        technologyLabel.setText("Technology Level: " + region.get().getTechnologyLevel());
        marketplaceLabel.setText("Marketplaces: ");
    }
}
