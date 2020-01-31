import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class MyPointBar extends ProgressBar {
    private int point;
    private int maxPoint;
    private final static int[] COLOR_MAX_POINT = {38, 125, 255};
    private final static int[] COLOR_ZERO_POINT = {255, 71, 71};
    private final static String PROGRESS_BAR_COLOR = "-fx-accent:rgb(%d,%d,%d);";


    public MyPointBar(int point, int maxPoint) {
        super(0);
        this.point = point;
        this.maxPoint = maxPoint;
        this.getStylesheets().add("styles/my-point-bar.css");
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
    }

    public void setPoint(int point) {
        this.point = point;
        int[] color = getBarColor(1.0 * point/maxPoint);
        this.setStyle(String.format(PROGRESS_BAR_COLOR,color[0], color[1], color[2]));
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(this.progressProperty(), 1.0 * point/maxPoint);
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
}
