import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class MyProgressBar extends ProgressBar {
    @Deprecated
    private int point;
    @Deprecated
    private int maxPoint;
    private SimpleIntegerProperty pointProperty;
    private SimpleIntegerProperty maxPointProperty;
    private SimpleStringProperty progressStringProperty;
    private static final int[] COLOR_MAX_POINT = {255, 255, 255};
    private static final int[] COLOR_ZERO_POINT = {255, 255, 255};
    private static final String PROGRESS_BAR_COLOR = "-fx-accent:rgb(%d,%d,%d);";


    public MyProgressBar(int point, int maxPoint) {
        super(0);
        this.point = point;
        this.maxPoint = maxPoint;
        this.pointProperty = new SimpleIntegerProperty(point);
        this.maxPointProperty = new SimpleIntegerProperty(maxPoint);
        this.progressStringProperty = new SimpleStringProperty();
        this.progressStringProperty.bind(Bindings.format("%s/%s", pointProperty, maxPointProperty));
        this.getStylesheets().add("styles/my-progress-bar.css");
        setPoint(point);
    }

    public int getPoint() {
        return point;
    }

    public int getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(int maxPoint) {
        this.maxPoint = maxPoint;
        this.maxPointProperty.setValue(maxPoint);
    }

    public void setPoint(int point) {
        this.point = point;
        this.pointProperty.set(point);
        int[] color = getBarColor(1.0 * point / maxPoint);
        this.setStyle(String.format(PROGRESS_BAR_COLOR, color[0], color[1], color[2]));
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(this.progressProperty(), 1.0 * point / maxPoint);
        KeyFrame keyFrame = new KeyFrame(new Duration(1000), keyValue);
        timeline.getKeyFrames().addAll(keyFrame);
        timeline.play();
    }

    private static int[] getBarColor(double progress) {
        int r = (int) (COLOR_MAX_POINT[0] * progress + COLOR_ZERO_POINT[0] * (1 - progress));
        int g = (int) (COLOR_MAX_POINT[1] * progress + COLOR_ZERO_POINT[1] * (1 - progress));
        int b = (int) (COLOR_MAX_POINT[2] * progress + COLOR_ZERO_POINT[2] * (1 - progress));
        return new int[] {r, g, b};
    }

    public MyGridPane withLabel() {
        Label progressLabel = new Label();
        progressLabel.textProperty().bind(progressStringProperty);
        double[] columnConstraints = {15, 85};
        MyGridPane myGridPane = new MyGridPane(MyGridPane.getSpan(1), columnConstraints, HPos.LEFT, VPos.CENTER);
        myGridPane.addRow(0, progressLabel, this);
        return myGridPane;
    }
}
