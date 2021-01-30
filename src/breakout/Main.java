package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * TODO
 */
public class Main extends Application {
    public static final String TITLE = "Breakout";
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final int BOUNCER_SPEED = 30;
    public static final Paint MOVER_COLOR = Color.PLUM;
    public static final int MOVER_SIZE = 50;
    public static final int MOVER_SPEED = 5;

    // some things needed to remember during game
    private Scene scene;
    private ImageView bouncer;
    private Rectangle mover;
    private KeyboardInputManager inputManager;


    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start(Stage stage) {
        inputManager = new KeyboardInputManager();

        // attach scene to the stage and display it
        scene = setupGame(SIZE, SIZE, BACKGROUND);
        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame(int width, int height, Paint background) {
        // register input manager
        scene.addEventHandler(KeyEvent.ANY, inputManager);

        // create one top level collection to organize the things in the scene
        Group root = new Group();
        // make some shapes and set their properties
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        bouncer = new ImageView(image);
        // x and y represent the top left corner, so center it in window
        bouncer.setX(width / 2 - bouncer.getBoundsInLocal().getWidth() / 2);
        bouncer.setY(height / 2 - bouncer.getBoundsInLocal().getHeight() / 2);
        mover = new Rectangle(width / 2 - MOVER_SIZE / 2, height / 2 - 100, MOVER_SIZE, MOVER_SIZE);
        mover.setFill(MOVER_COLOR);

        // order added to the group is the order in which they are drawn
        root.getChildren().add(bouncer);
        root.getChildren().add(mover);

        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    private void step(double elapsedTime) {
        // update "actors" attributes
        bouncer.setX(bouncer.getX() + BOUNCER_SPEED * elapsedTime);
        mover.setRotate(mover.getRotate() - 1);

        // Shape intersection = Shape.intersect(mover, grower);
        // if (intersection.getBoundsInLocal().getWidth() != -1) {
    }

    // What to do each time a key is pressed
    private void handleKeyInput(KeyCode code) {
        // TYPICAL way to do it, definitely more readable for longer actions
        if (code == KeyCode.RIGHT) {
            mover.setX(mover.getX() + MOVER_SPEED);
        } else if (code == KeyCode.LEFT) {
            mover.setX(mover.getX() - MOVER_SPEED);
        } else if (code == KeyCode.UP) {
            mover.setY(mover.getY() - MOVER_SPEED);
        } else if (code == KeyCode.DOWN) {
            mover.setY(mover.getY() + MOVER_SPEED);
        }
    }

    // What to do each time a key is pressed
    private void handleMouseInput(double x, double y) {
        // if (grower.contains(x, y)) {
        //     grower.setScaleX(grower.getScaleX() * GROWER_RATE);
        //     grower.setScaleY(grower.getScaleY() * GROWER_RATE);
        // }
    }

    /**
     * Start the program.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
