package uicomponents;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.sql.Time;


public class FightAnimationPane extends Pane {
    private ImageView myImage;
    private ImageView npcImage;
    private ImageView explosionOnMe;
    private ImageView explosionOnNPC;
    private ImageView laser;
    private ImageView bulletFromMe;
    private ImageView bulletFromNPC;

    private static final int moveDisplayTime = 50;


    public FightAnimationPane(String myImage, String npcImage) {
        super();
        this.myImage = new ImageView(myImage);
        this.myImage.setFitHeight(400);
        this.myImage.setRotate(270);
        this.npcImage = new ImageView(npcImage);
        this.npcImage.setFitHeight(400);
        this.npcImage.setRotate(90);
        this.explosionOnMe = new ImageView();
        this.explosionOnMe.setVisible(false);
        this.explosionOnNPC = new ImageView();
        this.explosionOnNPC.setVisible(false);
        this.bulletFromMe = new ImageView();
        this.bulletFromMe.setVisible(false);
        this.bulletFromNPC = new ImageView();
        this.bulletFromNPC.setVisible(false);
        this.laser = new ImageView("file:src/images/animation/laser.gif");
        this.laser.setPreserveRatio(false);
        this.laser.setVisible(false);
    }

    public void loopAnimation() {
        PauseTransition finishTimer = new PauseTransition(Duration.seconds(1));
        finishTimer.setOnFinished(event -> {
            generateMyAttack(finishTimer);
        });
        generateMyAttack(finishTimer);
    }

    private void generateMyAttack(PauseTransition finishTimer) {
        int i = (int) (Math.random() * 2);
        PauseTransition timer1 = new PauseTransition(Duration.seconds(1));
        timer1.setOnFinished(event -> {
            laser.setVisible(false);
            bulletFromMe.setVisible(false);
            this.explosionOnNPC.setImage(new Image(getRandomExplosion()));
            this.explosionOnNPC.setVisible(true);
            PauseTransition timer2 = new PauseTransition(Duration.seconds(1));
            timer2.setOnFinished(event1 -> {
                this.explosionOnNPC.setVisible(false);
                finishTimer.play();
            });
        });
        if (i == 0) { //Display Laser
            laser.setVisible(true);
        } else {
            bulletFromMe.setImage(new Image(getRandomCannon()));
            bulletFromMe.setLayoutX(getMyPositionX());
            bulletFromMe.setVisible(true);
            Timeline timeline = new Timeline();
            KeyValue myKeyValue = new KeyValue(bulletFromMe.layoutXProperty(), getNPCPositionX());
            KeyFrame myKeyFrame = new KeyFrame(new Duration(1000), myKeyValue);
            timeline.getKeyFrames().add(myKeyFrame);
            timeline.play();
        }
        timer1.play();
    }

    private double getMyPositionX() {
        return myImage.getLayoutX() + myImage.getImage().getWidth() / 2;
    }

    private double getMyPositionY() {
        return myImage.getLayoutY() + myImage.getImage().getHeight() / 2;
    }

    private double getNPCPositionX() {
        return npcImage.getLayoutX() + npcImage.getImage().getWidth() / 2;
    }

    private double getNPCPositionY() {
        return npcImage.getLayoutY() + npcImage.getImage().getHeight() / 2;
    }

    private String getRandomExplosion() {
        int i = (int) (Math.random() * 5);
        return "file:src/images/animation/" + i + ".gif";
    }

    private String getRandomCannon() {
        int i = (int) (Math.random() * 2);
        return "file:src/images/animation/cannon" + i + ".gif";
    }

    public void adjustImagePosition(ReadOnlyDoubleProperty widthProperty,
                                    ReadOnlyDoubleProperty heightProperty) {
        this.npcImage.layoutXProperty().bind(
                widthProperty.subtract(npcImage.getImage().widthProperty()).subtract(200));
        this.npcImage.layoutYProperty().bind(
                heightProperty.
                        divide(2).
                        subtract(npcImage.getImage().heightProperty().divide(2)));
        this.myImage.setLayoutX(200);
        this.myImage.layoutYProperty().bind(
                heightProperty.
                        divide(2).
                        subtract(myImage.getImage().heightProperty().divide(2)));

        this.explosionOnNPC.layoutXProperty().bind(
                widthProperty.subtract(explosionOnNPC.getImage().widthProperty()).subtract(200));
        this.explosionOnNPC.layoutYProperty().bind(
                heightProperty.
                        divide(2).
                        subtract(explosionOnNPC.getImage().heightProperty().divide(2)));
        this.explosionOnMe.setLayoutX(200);
        this.explosionOnMe.layoutYProperty().bind(
                heightProperty.
                        divide(2).
                        subtract(explosionOnMe.getImage().heightProperty().divide(2)));

        this.laser.layoutXProperty().bind(
                myImage.layoutXProperty().add(myImage.getImage().widthProperty()));
        this.laser.layoutYProperty().bind(
                heightProperty.
                        divide(2).
                        subtract(laser.getImage().heightProperty().divide(2)));
        this.laser.fitWidthProperty().bind(
                npcImage.layoutXProperty().subtract(laser.layoutXProperty()));
        this.bulletFromMe.layoutYProperty().bind(
                heightProperty.
                        divide(2).
                        subtract(bulletFromMe.getImage().heightProperty().divide(2)));
        this.bulletFromNPC.layoutYProperty().bind(
                heightProperty.
                        divide(2).
                        subtract(bulletFromNPC.getImage().heightProperty().divide(2)));
    }

    private void getCharactersToMoveUpAndDown() {

    }
}
