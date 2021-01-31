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

public class StatusDisplay extends UIComponent {

  private int lives;
  private PowerUpType powerUp;
  private Text lifeCount;
  private ImageView powerUpIndicator;

  private static final Map<PowerUpType, String> powerUpIcons = Map.of(
      PowerUpType.NONE, "",
      PowerUpType.HIGH_SPEED_BALL, "extraballpower.gif",
      PowerUpType.LARGE_BALL, "sizepower.gif",
      PowerUpType.WIDE_PADDLE, "paddlepower.gif"
  );

  public StatusDisplay(Vec2D pos, int lives, PowerUpType powerUp) {
    super(pos);
    this.lives = lives;
    this.powerUp = powerUp;
  }

  @Override
  protected void buildTree() {
    // remaining lives display
    lifeCount = new Text("" + lives);
    lifeCount.setX(pos.getX() + 25);
    lifeCount.setY(pos.getY() + 20);
    lifeCount.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
    lifeCount.setFill(Color.BLACK);
    lifeCount.setStroke(Color.WHITE);
    lifeCount.setStrokeWidth(1.0);

    Image heartImage = new Image(Objects.requireNonNull(
        this.getClass().getClassLoader().getResourceAsStream("heart.png")
    ));
    ImageView heartIcon = new ImageView(heartImage);
    heartIcon.setX(pos.getX() + 18);
    heartIcon.setY(pos.getY() + 3);
    heartIcon.setFitWidth(25);
    heartIcon.setFitHeight(25);

    // power up icon
    powerUpIndicator = new ImageView();
    updatePowerUpIcon();
    powerUpIndicator.setX(pos.getX() + 5);
    powerUpIndicator.setY(pos.getY() + 8);
    powerUpIndicator.setFitWidth(10);
    powerUpIndicator.setFitHeight(10);

    // add to child components
    children.add(heartIcon);
    children.add(lifeCount);
    children.add(powerUpIndicator);
  }

  public void setLives(int lives) {
    this.lives = lives;
    lifeCount.setText("" + lives);
  }

  private void updatePowerUpIcon() {
    var imageFile = this.getClass().getClassLoader().getResourceAsStream(powerUpIcons.get(powerUp));
    Image image = null;
    if (imageFile != null) {
      image = new Image(imageFile);
    }
    powerUpIndicator.setImage(image);
  }

  public void setPowerUp(PowerUpType powerUp) {
    this.powerUp = powerUp;
    updatePowerUpIcon();
  }
}
