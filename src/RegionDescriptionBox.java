import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RegionDescriptionBox extends VBox {
    private Region region;
    private Label nameLabel;
    private Label descriptionLabel;
    private Label technologyLabel;
    private Label marketplaceLabel;
    public RegionDescriptionBox(Game game, Region region, double x, double y) {
        super();
        this.region = region;
        this.nameLabel = new Label("");
        this.descriptionLabel = new Label("");
        this.technologyLabel = new Label("");
        this.marketplaceLabel = new Label("");

        if (region.equals(game.getCurrentRegion())) {
            setLabelsFull();
        } else {
            setLabelsOnlyName();
        }

        region.foundProperty().addListener((observable, oldValue, newValue) -> {
            setLabelsOnlyName();
        });

        this.getChildren().addAll(nameLabel, descriptionLabel, technologyLabel);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.toBack();
    }

    public void setRegion(Region region) {
        this.region = region;
        nameLabel.setText("Name: " + region.getName());
        descriptionLabel.setText("Description: " + region.getDescription());
        technologyLabel.setText("Technology Level: " + region.getTechnologyLevel());
        marketplaceLabel = new Label("Marketplaces: ");
    }
    public Region getRegion() {
        return region;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public Label getDescriptionLabel() {
        return descriptionLabel;
    }

    public Label getTechnologyLabel() {
        return technologyLabel;
    }

    public void setLabelsFull() {
        nameLabel.setText("Name: " + region.getName());
        descriptionLabel.setText("Description: " + region.getDescription());
        technologyLabel.setText("Technology Level: " + region.getTechnologyLevel());
        marketplaceLabel = new Label("Marketplaces: ");
    }

    public void setLabelsOnlyName() {
        if (region.isFound()) {
            nameLabel.setText(region.getName());
            descriptionLabel.setText("");
            technologyLabel.setText("");
            marketplaceLabel = new Label("");
        } else {
            nameLabel.setText("???");
            descriptionLabel.setText("");
            technologyLabel.setText("");
            marketplaceLabel = new Label("");
        }
    }
}
