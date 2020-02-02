import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class ButtonScaleHover extends ButtonSkin {
    public ButtonScaleHover(Button control) {
        super(control);
        ScaleTransition submitS = new ScaleTransition();
        submitS.setCycleCount(1);
        submitS.setDuration(Duration.millis(200));
        submitS.setInterpolator(Interpolator.EASE_BOTH);
        submitS.setNode(control);


        control.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                submitS.setFromX(control.getScaleX());
                submitS.setFromY(control.getScaleY());
                submitS.setToX(1.2);
                submitS.setToY(1.2);
                submitS.playFromStart();
            }
        });

        control.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                submitS.setFromX(control.getScaleX());
                submitS.setFromY(control.getScaleY());
                submitS.setToX(1);
                submitS.setToY(1);
                submitS.playFromStart();
            }
        });

    }
}
