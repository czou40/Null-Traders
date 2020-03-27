package uicomponents;

import screens.Screen;
import javafx.scene.control.Button;

public class MyNavigationButton extends Button {
    public MyNavigationButton(String name, Screen link) {
        this(name, link, true);
    }

    public MyNavigationButton(String name, Screen link, boolean displayInLabelStyle) {
        super(name);
        if (displayInLabelStyle) {
            this.getStylesheets().add("styles/my-navigation-button.css");
        }
        if (link != null) {
            setLink(link);
        }
    }

    public void setLink(Screen screen) {
        this.setOnAction(e -> {
            screen.display();
        });
    }
}
