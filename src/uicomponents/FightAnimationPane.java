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


public class FightAnimationPane extends Pane {
    private ImageView myImage;
    private ImageView npcImage;
    private ImageView explosionOnMe;
    private ImageView explosionOnNPC;
    private ImageView laser;
    private ImageView bulletFromMe;
    private ImageView bulletFromNPC;
    private PauseTransition finishTimer;
    private static final int IMAGE_SIZE = 350;
    private static final int BORDER_SIZE = 150;
    private static final double BULLET_SPEED = 0.5;
    private static final int EXPLOSION_DISPLAY_TIME = 500;
    private static final int ATTACK_INTERVAL = 200;


    public FightAnimationPane(String myImagePath, String npcImagePath) {
        super();
        this.myImage = new ImageView(myImagePath);
        this.myImage.setFitHeight(IMAGE_SIZE);
        this.myImage.setFitWidth(IMAGE_SIZE);
        this.myImage.setRotate(270);
        this.myImage.setStyle("-fx-background-color:#000000;");
        this.npcImage = new ImageView(npcImagePath);
        this.npcImage.setFitHeight(IMAGE_SIZE);
        this.npcImage.setFitWidth(IMAGE_SIZE);
        this.npcImage.setRotate(90);
        this.explosionOnMe = new ImageView();
        this.explosionOnMe.setFitWidth(IMAGE_SIZE);
        this.explosionOnMe.setFitHeight(IMAGE_SIZE);
        this.explosionOnMe.setVisible(false);
        this.explosionOnNPC = new ImageView();
        this.explosionOnNPC.setFitWidth(IMAGE_SIZE);
        this.explosionOnNPC.setFitHeight(IMAGE_SIZE);
        this.explosionOnNPC.setVisible(false);
        this.bulletFromMe = new ImageView();
        this.bulletFromMe.setFitWidth(IMAGE_SIZE);
        this.bulletFromMe.setFitHeight(IMAGE_SIZE);
        this.bulletFromMe.setVisible(false);
        this.bulletFromNPC = new ImageView();
        this.bulletFromNPC.setRotate(180);
        this.bulletFromNPC.setFitWidth(IMAGE_SIZE);
        this.bulletFromNPC.setFitHeight(IMAGE_SIZE);
        this.bulletFromNPC.setVisible(false);
        this.laser = new ImageView("file:src/images/animation/laser.gif");
        this.laser.setPreserveRatio(false);
        this.laser.setFitHeight(IMAGE_SIZE);
        this.laser.setVisible(false);
        this.getChildren().addAll(
                laser, myImage, npcImage, explosionOnMe, explosionOnNPC, bulletFromMe, bulletFromNPC);
        this.getStylesheets().add("styles/test.css");
    }

    public void loopAnimation() {
        finishTimer = new PauseTransition(Duration.millis(ATTACK_INTERVAL));
        finishTimer.setOnFinished(event -> {
            int rand = (int) (Math.random() * 2);
            if (rand == 0) {
                generateMyAttack(finishTimer);
            } else {
                generateNPCAttack(finishTimer);
            }
        });
        generateMyAttack(finishTimer);
    }

    public void stopAnimation() {
        finishTimer.setOnFinished(event -> {

        });
        explosionOnMe.setVisible(false);
        explosionOnNPC.setVisible(false);
        bulletFromNPC.setVisible(false);
        bulletFromMe.setVisible(false);
        laser.setVisible(false);
    }

    private void generateMyAttack(PauseTransition finishTimer) {
        int i = (int) (Math.random() * 2);
        PauseTransition timer1 = new PauseTransition(Duration.millis(200.0 / BULLET_SPEED));
        timer1.setOnFinished(event -> {
            laser.setVisible(false);
            bulletFromMe.setVisible(false);
            this.explosionOnNPC.setImage(new Image(getRandomExplosion()));
            this.explosionOnNPC.setVisible(true);
            PauseTransition timer2 = new PauseTransition(Duration.millis(EXPLOSION_DISPLAY_TIME));
            timer2.setOnFinished(event1 -> {
                this.explosionOnNPC.setVisible(false);
            });
            timer2.play();
            finishTimer.play();
        });
        if (i == 0) { //Display Laser
            laser.setFitWidth(0);
            laser.setLayoutX(myImage.getLayoutX() + IMAGE_SIZE);
            laser.setVisible(true);
            Timeline timeline = new Timeline();
            KeyValue myKeyValue = new KeyValue(laser.fitWidthProperty(), getLaserLength());
            KeyFrame myKeyFrame = new KeyFrame(new Duration(200.0 / BULLET_SPEED), myKeyValue);
            timeline.getKeyFrames().add(myKeyFrame);
            timeline.play();
        } else {
            bulletFromMe.setImage(new Image(getRandomCannon()));
            bulletFromMe.setLayoutX(myImage.getLayoutX());
            bulletFromMe.setVisible(true);
            Timeline timeline = new Timeline();
            KeyValue myKeyValue = new KeyValue(
                    bulletFromMe.layoutXProperty(), npcImage.getLayoutX());
            KeyFrame myKeyFrame = new KeyFrame(new Duration(200.0 / BULLET_SPEED), myKeyValue);
            timeline.getKeyFrames().add(myKeyFrame);
            timeline.play();
        }
        timer1.play();
    }

    private void generateNPCAttack(PauseTransition finishTimer) {
        int i = (int) (Math.random() * 2);
        PauseTransition timer1 = new PauseTransition(Duration.millis(200.0 / BULLET_SPEED));
        timer1.setOnFinished(event -> {
            laser.setVisible(false);
            bulletFromNPC.setVisible(false);
            this.explosionOnMe.setImage(new Image(getRandomExplosion()));
            this.explosionOnMe.setVisible(true);
            PauseTransition timer2 = new PauseTransition(Duration.millis(EXPLOSION_DISPLAY_TIME));
            timer2.setOnFinished(event1 -> {
                this.explosionOnMe.setVisible(false);
            });
            timer2.play();
            finishTimer.play();
        });
        if (i == 0) { //Display Laser
            laser.setFitWidth(0);
            laser.setLayoutX(npcImage.getLayoutX());
            laser.setVisible(true);
            Timeline timeline = new Timeline();
            KeyValue myKeyValue1 = new KeyValue(laser.fitWidthProperty(), getLaserLength());
            KeyValue myKeyValue2 = new KeyValue(
                    laser.layoutXProperty(),
                    myImage.getLayoutX() + IMAGE_SIZE);
            KeyFrame myKeyFrame = new KeyFrame(
                    new Duration(200.0 / BULLET_SPEED),
                    myKeyValue1,
                    myKeyValue2);
            timeline.getKeyFrames().add(myKeyFrame);
            timeline.play();
        } else {
            bulletFromNPC.setImage(new Image(getRandomCannon()));
            bulletFromNPC.setLayoutX(npcImage.getLayoutX());
            bulletFromNPC.setVisible(true);
            Timeline timeline = new Timeline();
            KeyValue myKeyValue = new KeyValue(
                    bulletFromNPC.layoutXProperty(), myImage.getLayoutX());
            KeyFrame myKeyFrame = new KeyFrame(new Duration(200.0 / BULLET_SPEED), myKeyValue);
            timeline.getKeyFrames().add(myKeyFrame);
            timeline.play();
        }
        timer1.play();
    }

    private double getLaserLength() {
        return npcImage.getLayoutX() - myImage.getLayoutX() - IMAGE_SIZE / 2.0;
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
        this.npcImage.layoutXProperty().bind(widthProperty.subtract(BORDER_SIZE + IMAGE_SIZE));
        this.npcImage.layoutYProperty().bind(heightProperty.divide(2).subtract(IMAGE_SIZE / 2));
        this.myImage.setLayoutX(BORDER_SIZE);
        this.myImage.layoutYProperty().bind(heightProperty.divide(2).subtract(IMAGE_SIZE / 2));
        this.explosionOnNPC.layoutXProperty().bind(npcImage.layoutXProperty());
        this.explosionOnNPC.layoutYProperty().bind(npcImage.layoutYProperty());
        this.explosionOnMe.setLayoutX(BORDER_SIZE);
        this.explosionOnMe.layoutYProperty().bind(myImage.layoutYProperty());
        this.laser.layoutYProperty().bind(heightProperty.divide(2).subtract(IMAGE_SIZE / 2));
        this.bulletFromMe.layoutYProperty().
                bind(heightProperty.divide(2).subtract(IMAGE_SIZE / 2));
        this.bulletFromNPC.layoutYProperty().
                bind(heightProperty.divide(2).subtract(IMAGE_SIZE / 2));
    }
}
