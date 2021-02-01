package breakout;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * Base class of all game objects that can move and collide
 */
public class GameObject {

  protected Vec2D pos;
  protected ImageView sceneNode;
  protected Collider collider;

  /**
   * Get the javafx Node object
   */
  public Node getSceneNode() {
    return sceneNode;
  }

  /**
   * Get the collider of this game object
   */
  public Collider getCollider() {
    return collider;
  }

  /**
   * Called each frame
   */
  public void step(double time) {
  }

  /**
   * Get the position of the object
   */
  public Vec2D getPos() {
    return pos;
  }

  /**
   * Handle the collision occurred between this object and others
   *
   * @param collision The information about the collision
   */
  public void handleCollision(Collision collision) {
  }
}
