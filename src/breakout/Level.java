package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

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

        // x and y represent the top left corner, so center it in window
        double screen_half_width = (double) Main.SCREEN_WIDTH / 2;
        double screen_half_height = (double) Main.SCREEN_HEIGHT / 2;

        // init ball
        ball = new Ball(screen_half_width, screen_half_height);

        // init paddle
        paddle = new Paddle(screen_half_width, Main.SCREEN_HEIGHT - 20);

        // init 4 blocks at screen edges
        blocks = new ArrayList<>();
        blocks.add(new Block(                                              // left
                Block.BlockType.INDESTRUCTIBLE,
                new Vec2D(-100, -100),
                new Vec2D(0, Main.SCREEN_HEIGHT + 100))
        );
        blocks.add(new Block(                                              // right
                Block.BlockType.INDESTRUCTIBLE,
                new Vec2D(Main.SCREEN_WIDTH, -100),
                new Vec2D(Main.SCREEN_WIDTH + 100, Main.SCREEN_HEIGHT + 100))
        );
        blocks.add(new Block(                                              // top
                Block.BlockType.INDESTRUCTIBLE,
                new Vec2D(-100, -100),
                new Vec2D(Main.SCREEN_WIDTH + 100, 0))
        );
        blocks.add(new Block(                                              // bottom
                Block.BlockType.INDESTRUCTIBLE,
                new Vec2D(-100, Main.SCREEN_HEIGHT),
                new Vec2D(Main.SCREEN_WIDTH + 100, Main.SCREEN_HEIGHT + 100))
        );

        // order added to the group is the order in which they are drawn
        root.getChildren().add(ball.getSceneNode());
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
        ball.step(time);
        paddle.step(time);

        // check collisions
        checkAndHandleBallCollision(paddle.getCollider());
        for (Block b : blocks) {
            checkAndHandleBallCollision(b.getCollider());
        }
    }

    private void checkAndHandleBallCollision(Collider b) {
        Collision collision = Collider.checkCollision(ball.getCollider(), b);
        if (collision != null) {
            BallCollisionHandler.handle(ball, collision, poweredUp);
        }
    }

    public void checkVictory() {
    }

    public void cheat(CheatType type) {
    }

    public void checkAndHandleCollision() {
    }
}
