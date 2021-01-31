package breakout;

import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Update only when necessary, so no `step()` method
 */
public class StatusDisplay {

  public Vec2D pos;
  private int lives;
  private PowerUpType powerUp;
  private Group sceneNode;
  private Text lifeCount;
  private ImageView powerUpIndicator;

  private static final Map<PowerUpType, String> powerUpIcons = Map.of(
      PowerUpType.NONE, "",
      PowerUpType.HIGH_SPEED_BALL, "extraballpower.gif",
      PowerUpType.LARGE_BALL, "sizepower.gif",
      PowerUpType.NO_PENALTY_WARP, "paddlepower.gif"
  );

  public StatusDisplay(Vec2D pos, int lives, PowerUpType powerUp) {
    this.pos = pos;
    this.lives = lives;
    this.powerUp = powerUp;

    // remaining lives display
    lifeCount = new Text("" + lives);
    lifeCount.setX(pos.getX() + 20);
    lifeCount.setY(pos.getY() + 20);
    lifeCount.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
    lifeCount.setFill(Color.BLACK);
    lifeCount.setStroke(Color.WHITE);
    lifeCount.setStrokeWidth(1.0);

    // power up icon
    powerUpIndicator = new ImageView();
    updatePowerUpIcon();
    powerUpIndicator.setX(pos.getX() + 5);
    powerUpIndicator.setY(pos.getY() + 5);
    powerUpIndicator.setFitWidth(10);
    powerUpIndicator.setFitHeight(10);

    // combine elements
    sceneNode = new Group();
    sceneNode.getChildren().add(powerUpIndicator);
    sceneNode.getChildren().add(lifeCount);

    // move UI nodes to the front
    sceneNode.setViewOrder(-1000);
  }

  public Node getSceneNode() {
    return sceneNode;
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
