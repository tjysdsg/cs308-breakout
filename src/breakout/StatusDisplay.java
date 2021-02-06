package breakout;

import java.util.Map;
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * UI component for displaying game status on top the of screen (remaining live, scores, level name,
 * etc.)
 */
public class StatusDisplay extends UIComponent {

  private int lives;
  private int score = 0;
  private int levelIdx;
  private PowerUpType powerUp;
  private Text lifeCount;
  private Text scoreDisplay;
  private Text levelDisplay;
  private ImageView powerUpIndicator;

  private static final Map<PowerUpType, String> powerUpIcons = Map.of(
      PowerUpType.NONE, "",
      PowerUpType.HIGH_SPEED_BALL, "extraballpower.gif",
      PowerUpType.LARGE_BALL, "sizepower.gif",
      PowerUpType.WIDE_PADDLE, "paddlepower.gif"
  );

  public StatusDisplay(int lives, PowerUpType powerUp, int levelIdx) {
    super();
    this.lives = lives;
    this.powerUp = powerUp;
    this.levelIdx = levelIdx;
  }

  /**
   * @see UIComponent#buildTree
   */
  @Override
  protected void buildTree() {
    // remaining lives display
    lifeCount = new Text("" + lives);
    lifeCount.setX(25);
    lifeCount.setY(20);
    lifeCount.setFont(Font.font("Arial Black", FontWeight.BOLD, FontPosture.REGULAR, 15));
    lifeCount.setFill(Color.BLACK);
    lifeCount.setStroke(Color.WHITE);
    lifeCount.setStrokeWidth(1.0);

    // heart icon
    Image heartImage = new Image(Objects.requireNonNull(
        this.getClass().getClassLoader().getResourceAsStream("heart.png")
    ));
    ImageView heartIcon = new ImageView(heartImage);
    heartIcon.setX(18);
    heartIcon.setY(3);
    heartIcon.setFitWidth(25);
    heartIcon.setFitHeight(25);

    // score display
    scoreDisplay = new Text("Score: " + score);
    scoreDisplay.setX(50);
    scoreDisplay.setY(20);
    scoreDisplay.setFont(Font.font("Arial Black", FontWeight.BOLD, FontPosture.REGULAR, 15));
    scoreDisplay.setFill(Color.BLUE);
    scoreDisplay.setStroke(Color.WHITE);
    scoreDisplay.setStrokeWidth(1.0);

    // power up icon
    powerUpIndicator = new ImageView();
    updatePowerUpIcon();
    powerUpIndicator.setX(5);
    powerUpIndicator.setY(8);
    powerUpIndicator.setFitWidth(10);
    powerUpIndicator.setFitHeight(10);

    // level display
    levelDisplay = new Text("Level " + levelIdx);
    levelDisplay.setX(Main.SCREEN_WIDTH - 100);
    levelDisplay.setY(20);
    levelDisplay.setFont(Font.font("Arial Black", FontWeight.BOLD, FontPosture.REGULAR, 15));
    levelDisplay.setFill(Color.GREEN);
    levelDisplay.setStroke(Color.WHITE);
    levelDisplay.setStrokeWidth(1.0);

    // add to child components
    children.add(heartIcon);
    children.add(lifeCount);
    children.add(scoreDisplay);
    children.add(levelDisplay);
    children.add(powerUpIndicator);
  }

  /**
   * Set how many lives left
   */
  public void setLives(int lives) {
    this.lives = lives;
    lifeCount.setText("" + lives);
  }

  /**
   * Set the current level index
   */
  public void setLevelIdx(int levelIdx) {
    this.levelIdx = levelIdx;
    levelDisplay.setText("Level " + levelIdx);
  }

  /**
   * Set the scores achieved
   */
  public void setScore(int score) {
    this.score = score;
    scoreDisplay.setText("Score: " + score);
  }

  private void updatePowerUpIcon() {
    var imageFile = this.getClass().getClassLoader().getResourceAsStream(powerUpIcons.get(powerUp));
    Image image = null;
    if (imageFile != null) {
      image = new Image(imageFile);
    }
    powerUpIndicator.setImage(image);
  }

  /**
   * Set the current activated power up
   */
  public void setPowerUp(PowerUpType powerUp) {
    this.powerUp = powerUp;
    updatePowerUpIcon();
  }
}
