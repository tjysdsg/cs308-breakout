package breakout;

import breakout.Block.BlockType;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Level {

  private enum CheatType {
    TRIGGER_EXPLOSION,
    WIDE_PADDLE,
    REMOVE_INDESTRUCTIBLE,
    ONE_HIT_FORTIFIED,
  }

  private Scene scene;
  private Group root;
  private String levelName = "Breakout";
  private KeyboardInputManager inputManager;
  private PowerUpType powerUp = PowerUpType.NONE;
  private double powerUpTime = 0;
  private int lives = 3;
  private ArrayList<Block> blocks;
  private Ball ball;
  private Paddle paddle;
  private StatusDisplay statusDisplay;
  private SplashScreen splashScreen;
  private boolean won = false;
  private int score = 0;

  private static final int BLOCK_HEIGHT = 20;
  private static final double DEFAULT_BALL_MAX_VELOCITY = 200;
  private static final double DEFAULT_BALL_RADIUS = 10;
  private static final double DEFAULT_PADDLE_WIDTH = 80;
  private static final double POWERUP_BALL_MAX_VELOCITY = 300;
  private static final double POWERUP_BALL_RADIUS = 12;
  private static final double POWERUP_PADDLE_WIDTH = 110;

  private static final Map<Character, Block.BlockType> ASCII2BLOCK_TYPE = Map.of(
      '@', Block.BlockType.NORMAL,
      '#', Block.BlockType.FORTIFIED,
      '$', Block.BlockType.EXPLOSIVE,
      '*', Block.BlockType.INDESTRUCTIBLE,
      '=', Block.BlockType.MOVING
  );


  public Level() {
    // setup scene
    scene = setupGame(Color.AZURE);

    // TODO: detect touch screen and use a different input manager
    inputManager = KeyboardInputManager.globalInputManager();
    // register input handlers
    registerInputHandlers();
    // register the input manager
    scene.addEventHandler(KeyEvent.ANY, inputManager);
  }

  private void registerInputHandlers() {
    inputManager.registerInputHandler("Horizontal", val -> {
      paddle.translate((int) val);
    });

    // press "L" to add one extra life
    inputManager.registerInputHandler("L", val -> {
      if (val == 1) {
        ++lives;
        statusDisplay.setLives(lives);
      }
    });

    // press "R" to reset the level
    inputManager.registerInputHandler("R", val -> {
      if (val == 1) {
        resetLevel();
      }
    });
  }

  // Create the game's "scene": what shapes will be in the game and their starting properties
  private Scene setupGame(Paint background) {
    // create one top level collection to organize the things in the scene
    root = new Group();

    // x and y represent the top left corner, so center it in window
    double screen_half_width = Main.SCREEN_WIDTH / 2.0;
    double screen_half_height = Main.SCREEN_HEIGHT / 2.0;

    // init ball
    ball = new Ball(screen_half_width, screen_half_height);

    // init paddle
    paddle = new Paddle();

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
    // blocks.add(new Block(                                              // bottom
    //         Block.BlockType.INDESTRUCTIBLE,
    //         new Vec2D(-100, Main.SCREEN_HEIGHT),
    //         new Vec2D(Main.SCREEN_WIDTH + 100, Main.SCREEN_HEIGHT + 100))
    // );

    // UI elements
    statusDisplay = new StatusDisplay(new Vec2D(0, 0), lives, powerUp);
    statusDisplay.init();

    splashScreen = new SplashScreen();
    splashScreen.init();
    splashScreen.setShowRules(true);
    splashScreen.setShowWin(false);

    // TODO: ask user for input to start the game

    // add stuff to scene
    root.getChildren().add(ball.getSceneNode());
    root.getChildren().add(paddle.getSceneNode());
    root.getChildren().add(statusDisplay.getSceneNode());
    root.getChildren().add(splashScreen.getSceneNode());
    for (Block b : blocks) {
      root.getChildren().add(b.getSceneNode());
    }

    // create a place to see the shapes
    return new Scene(root, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, background);
  }

  public static Level fromLevelFile(String filename) {
    Level ret = new Level();

    String[] lines = Util.readResourceTxtToLines(filename);
    if (lines == null) {
      System.err.println("WARNING: Cannot read level file: " + filename);
      return ret;
    }

    int nCols = lines[0].length();
    int blockWidth = Main.SCREEN_WIDTH / nCols;

    for (int r = 0; r < lines.length; ++r) {
      for (int c = 0; c < nCols; ++c) {
        Vec2D p1 = new Vec2D(c * blockWidth, r * BLOCK_HEIGHT);
        Vec2D p2 = new Vec2D(p1.getX() + blockWidth, p1.getY() + BLOCK_HEIGHT);
        if (c >= lines[r].length()) {
          continue;
        }
        char ch = lines[r].charAt(c);
        if (ch != ' ') {
          Block block = new Block(
              ASCII2BLOCK_TYPE.get(ch),
              p1, p2
          );
          ret.blocks.add(block);
          ret.root.getChildren().add(block.getSceneNode());
        }
      }
    }

    return ret;
  }

  public Scene getScene() {
    return scene;
  }

  public String getLevelName() {
    return levelName;
  }

  public void step(double time) {
    powerUpTime += time;

    ball.step(time);
    paddle.step(time);

    // check collision between the ball and the paddle
    checkAndHandleBallCollision(paddle);

    ArrayList<Integer> blocksForRemoval = new ArrayList<>();
    for (int i = 0; i < blocks.size(); ++i) {
      Block b = blocks.get(i);

      // check collision between the ball and the blocks
      checkAndHandleBallCollision(b);

      // check block for removal
      if (b.getBlockType() == Block.BlockType.REMOVE) {
        triggerRandomPowerUp(); // trigger power up randomly
        blocksForRemoval.add(i);
      }
    }

    // remove blocks that are marked for removal
    blocksForRemoval.sort(Collections.reverseOrder());
    for (int idx : blocksForRemoval) {
      // increment score
      score += blocks.get(idx).score;
      statusDisplay.setScore(score);

      // remove from list and from scene
      root.getChildren().remove(blocks.get(idx).getSceneNode());
      blocks.remove(idx);
    }

    // disable powerups after 5 seconds
    if (powerUp != PowerUpType.NONE && powerUpTime >= 5.0) {
      triggerPowerUp(PowerUpType.NONE);
      powerUpTime = 0;
    }

    // check if ball is outside
    checkBallAndReset();

    // check if all (non-indestructible) blocks are cleared
    checkVictory();
  }

  private void checkAndHandleBallCollision(GameObject go) {
    Collision collision = Collider.checkCollision(ball.getCollider(), go.getCollider());
    if (collision != null) {
      ball.handleCollision(collision);
      go.handleCollision(collision);
    }
  }

  private void checkBallAndReset() {
    if (ball.getPos().getY() + ball.getRadius() >= Main.SCREEN_HEIGHT) {
      --lives;
      statusDisplay.setLives(lives);
      resetLevel();
    }
    if (lives <= 0) {
      // TODO: tell user no lives left
      System.out.println("You're dead");
    }
  }

  private void resetLevel() {
    ball.reset(Main.SCREEN_WIDTH / 2.0, Main.SCREEN_HEIGHT / 2.0);
    paddle.reset();
    triggerPowerUp(PowerUpType.NONE);
  }

  private void triggerRandomPowerUp() {
    // don't trigger if already in powerup
    if (powerUp != PowerUpType.NONE) {
      return;
    }

    Random random = new Random();
    PowerUpType[] powerUpTypes = PowerUpType.values();
    var p = powerUpTypes[random.nextInt(powerUpTypes.length)];
    if (p != PowerUpType.NONE) {
      triggerPowerUp(p);
    }
  }

  private void triggerPowerUp(PowerUpType type) {
    powerUp = type;
    statusDisplay.setPowerUp(type);
    System.out.println("Powerup triggered: " + type);

    if (type == PowerUpType.HIGH_SPEED_BALL) {
      // FIXME: velocity being too high (about 400) will break the collision detection (CCD needed)
      ball.setMaxVelocity(POWERUP_BALL_MAX_VELOCITY);
    } else if (type == PowerUpType.LARGE_BALL) {
      ball.setRadius(POWERUP_BALL_RADIUS);
    } else if (type == PowerUpType.WIDE_PADDLE) {
      paddle.setWidth(POWERUP_PADDLE_WIDTH);
    } else if (type == PowerUpType.NONE) {
      ball.setMaxVelocity(DEFAULT_BALL_MAX_VELOCITY);
      ball.setRadius(DEFAULT_BALL_RADIUS);
      paddle.setWidth(DEFAULT_PADDLE_WIDTH);
    }

    powerUpTime = 0;
  }

  public void cheat(CheatType type) {
  }

  public void checkVictory() {
    won = true;
    for (Block b : blocks) {
      if (b.getBlockType() == BlockType.INDESTRUCTIBLE || b.getBlockType() == BlockType.REMOVE) {
        continue;
      }
      won = false;
    }
    if (won) {
      splashScreen.setShowWin(true);
      // TODO: pause game for 5 seconds, and load the next level
    }
  }
}
