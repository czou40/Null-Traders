package uicomponents;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class MyProgressBar extends ProgressBar {
    private SimpleIntegerProperty pointProperty;
    private SimpleIntegerProperty maxPointProperty;
    private SimpleStringProperty progressStringProperty;
    private static final int[] COLOR_MAX_POINT = {255, 255, 255};
    private static final int[] COLOR_ZERO_POINT = {255, 255, 255};
    private static final String PROGRESS_BAR_COLOR = "-fx-accent:rgb(%d,%d,%d);";

    public SimpleIntegerProperty pointProperty() {
        return pointProperty;
    }

    public SimpleIntegerProperty maxPointProperty() {
        return maxPointProperty;
    }

    public SimpleStringProperty progressStringProperty() {
        return progressStringProperty;
    }

    public MyProgressBar(int point, int maxPoint) {
        super(0);
        this.pointProperty = new SimpleIntegerProperty(point);
        this.maxPointProperty = new SimpleIntegerProperty(maxPoint);
        this.progressStringProperty = new SimpleStringProperty();
        this.progressStringProperty.bind(Bindings.format("%s/%s", pointProperty, maxPointProperty));
        this.getStylesheets().add("styles/my-progress-bar.css");
        setPoint(point);
        this.pointProperty.addListener(e -> {
            playAnimation();
        });
    }

    public int getPoint() {
        return pointProperty.get();
    }

    public int getMaxPoint() {
        return maxPointProperty.get();
    }

    public void setMaxPoint(int maxPoint) {
        this.maxPointProperty.setValue(maxPoint);
    }

    public void setPoint(int point) {
        this.pointProperty.set(point);
        playAnimation();
    }

    private void playAnimation() {
        int[] color = getBarColor(1.0 * pointProperty.get() / maxPointProperty.get());
        this.setStyle(String.format(PROGRESS_BAR_COLOR, color[0], color[1], color[2]));
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(this.progressProperty(),
                pointProperty.doubleValue() / maxPointProperty.doubleValue());
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
        MyGridPane myGridPane = new MyGridPane(null, columnConstraints);
        myGridPane.addRow(0, progressLabel, this);
        return myGridPane;
    }
}
