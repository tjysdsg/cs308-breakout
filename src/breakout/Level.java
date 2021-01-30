package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
    private ArrayList<Block> blocks;
    private Ball ball;
    private Paddle paddle;

    // TODO: move to Ball and Paddle
    private ImageView bouncer;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final int BOUNCER_SPEED = 30;

    public Level() {
        // setup scene
        scene = setupGame(Color.AZURE);

        // register input manager
        // TODO: detect touch screen and use a different input manager
        inputManager = KeyboardInputManager.globalInputManager();
        inputManager.registerInputHandler("Horizontal", val -> {
            paddle.translate((int) val);
        });
        scene.addEventHandler(KeyEvent.ANY, inputManager);
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame(Paint background) {
        // create one top level collection to organize the things in the scene
        Group root = new Group();
        // make some shapes and set their properties
        Image image = new Image(Objects.requireNonNull(
                this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE)
        ));
        bouncer = new ImageView(image);
        // x and y represent the top left corner, so center it in window
        double screen_half_width = (double) Main.SCREEN_WIDTH / 2;
        double screen_half_height = (double) Main.SCREEN_HEIGHT / 2;
        bouncer.setX(screen_half_width - bouncer.getBoundsInLocal().getWidth() / 2);
        bouncer.setY(screen_half_height - bouncer.getBoundsInLocal().getHeight() / 2);

        // init paddle
        paddle = new Paddle(screen_half_width, Main.SCREEN_HEIGHT - 20);

        // order added to the group is the order in which they are drawn
        root.getChildren().add(bouncer);
        root.getChildren().add(paddle.getSceneNode());

        // create a place to see the shapes
        return new Scene(root, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, background);
    }

    public static Level fromLevelFile(String filename) {
        // TODO: read from level file
        return new Level();
    }

    public Scene getScene() {
        return scene;
    }

    public String getLevelName() {
        return levelName;
    }

    public void step(double time) {
        paddle.step(time);
        bouncer.setX(bouncer.getX() + BOUNCER_SPEED * time);
    }

    public void checkVictory() {
    }

    public void cheat(CheatType type) {
    }

    public void checkAndHandleCollision() {
    }
}
