package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * TODO
 */
public class Main extends Application {
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final int SCREEN_WIDTH = 800; // TODO: tell javafx to prevent window resizing
    public static final int SCREEN_HEIGHT = 400;

    Level level;

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start(Stage stage) {
        level = Level.fromLevelFile("level1.txt");
        // attach scene to the stage and display it
        stage.setScene(level.getScene());
        stage.setTitle(level.getLevelName());
        stage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step(double time) {
        level.step(time);
        // Shape intersection = Shape.intersect(mover, grower);
        // if (intersection.getBoundsInLocal().getWidth() != -1) {
    }

    /**
     * Start the program.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
