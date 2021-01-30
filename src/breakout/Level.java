package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Objects;

public class Level {
    private enum CheatType {
        TRIGGER_EXPLOSION,
        WIDE_PADDLE,
        REMOVE_INDESTRUCTIBLE,
        ONE_HIT_FORTIFIED,
    }

    private Scene scene;
    private String levelName = "Breakout";
    private KeyboardInputManager inputManager;
    private boolean poweredUp = false;
    private int lives = 3;
    private int screenWidth = 600;
    private int screenHeight = 800;
    private ArrayList<Block> blocks;
    private Ball ball;
    private Paddle paddle;

    // TODO: move to Ball and Paddle
    private ImageView bouncer;
    private Rectangle mover;
    public static final Paint MOVER_COLOR = Color.PLUM;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final int MOVER_SIZE = 50;
    public static final int BOUNCER_SPEED = 30;
    public static final int MOVER_SPEED = 5;
    Vec2D v = new Vec2D();

    public Level() {
        // setup scene
        scene = setupGame(screenWidth, screenHeight, Color.AZURE);

        // register input manager
        // TODO: detect touch screen and use a different input manager
        inputManager = KeyboardInputManager.globalInputManager();
        inputManager.registerInputHandler("Horizontal", val -> {
            v.setX(val);
        });
        inputManager.registerInputHandler("Vertical", val -> {
            // y axis is downwards
            v.setY(-val);
        });
        scene.addEventHandler(KeyEvent.ANY, inputManager);
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame(int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        Group root = new Group();
        // make some shapes and set their properties
        Image image = new Image(Objects.requireNonNull(
                this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE)
        ));
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
        return new Scene(root, width, height, background);
    }

    public static Level fromLevelFile(String filename) {
        return new Level();
    }

    public Scene getScene() {
        return scene;
    }

    public String getLevelName() {
        return levelName;
    }

    public void step(double time) {
        mover.setX(mover.getX() + v.getX() * MOVER_SPEED);
        mover.setY(mover.getY() + v.getY() * MOVER_SPEED);
        bouncer.setX(bouncer.getX() + BOUNCER_SPEED * time);
        mover.setRotate(mover.getRotate() - 1);
    }

    public void checkVictory() {
    }

    public void cheat(CheatType type) {
    }

    public void checkAndHandleCollision() {
    }
}
