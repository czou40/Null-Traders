package uicomponents;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Compass extends Pane {
    private SimpleDoubleProperty angle;
    private static final int SIZE = 200;
    private static final int ARROW_SIZE = 65;
    private static final String BACKGROUND_PATH = "file:src/images/compass/compass.png";
    private static final String ARROW_PATH = "file:src/images/compass/arrow.png";
    private static final double OSCILLATION_AMPLITUDE = 8;
    private static final double OSCILLATION_FREQUENCY = 0.05;
    private ImageView backgroundImage;
    private ImageView arrow;
    private Timeline compassOscillation;

    public Compass() {
        super();
        backgroundImage = new ImageView(BACKGROUND_PATH);
        backgroundImage.setPreserveRatio(false);
        backgroundImage.setFitWidth(SIZE);
        backgroundImage.setFitHeight(SIZE);
        this.getChildren().add(backgroundImage);
        arrow = new ImageView(ARROW_PATH);
        arrow.setFitHeight(ARROW_SIZE);
        arrow.setFitWidth(ARROW_SIZE);
        arrow.setLayoutX((SIZE - ARROW_SIZE) / 2.0);
        arrow.setLayoutY((SIZE - ARROW_SIZE) / 2.0);
        this.getChildren().add(arrow);
        angle = new SimpleDoubleProperty(0);
        startCompassOscillationAnimation();
    }

    private void startCompassOscillationAnimation() {
        compassOscillation = new Timeline();
        double variation = Math.random() * OSCILLATION_AMPLITUDE
                - OSCILLATION_AMPLITUDE * 1
                / (1 + Math.exp(-(OSCILLATION_FREQUENCY) * (arrow.getRotate() - angle.get())));
        KeyValue keyValue = new KeyValue(
                arrow.rotateProperty(), arrow.getRotate() + variation);
        KeyFrame keyFrame = new KeyFrame(new Duration(300), keyValue);
        compassOscillation.getKeyFrames().setAll(keyFrame);
        compassOscillation.setOnFinished(event -> {
            startCompassOscillationAnimation();
        });
        compassOscillation.play();
    }

    public void setAngle(double angle) {
        this.angle.set(angle);
        compassOscillation.stop();
        compassOscillation = new Timeline();
        KeyValue keyValue = new KeyValue(
                arrow.rotateProperty(), angle
                - (angle > arrow.getRotate()
                ? Math.random() * Math.min(15, angle - arrow.getRotate())
                : Math.random() * Math.max(-15, angle - arrow.getRotate())));
        KeyFrame keyFrame = new KeyFrame(new Duration(300), keyValue);
        compassOscillation.getKeyFrames().setAll(keyFrame);
        compassOscillation.setOnFinished(event -> {
            startCompassOscillationAnimation();
        });
        compassOscillation.play();
    }

    public void stopWorking() {
        compassOscillation.stop();
        compassOscillation = new Timeline();
        KeyValue keyValue = new KeyValue(
                arrow.rotateProperty(),  arrow.getRotate() + 30);
        KeyFrame keyFrame = new KeyFrame(new Duration(300), keyValue);
        compassOscillation.getKeyFrames().setAll(keyFrame);
        compassOscillation.setOnFinished(event -> {
            stopWorking();
        });
        compassOscillation.play();
    }

    public double getAngle() {
        return this.angle.get();
    }

    public SimpleDoubleProperty angleProperty() {
        return this.angle;
    }
}
