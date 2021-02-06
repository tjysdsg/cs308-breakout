package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Handles movement and collision of the ball.
 * <p>
 * NOTE: pos is the center of the ball
 */
public class Ball extends GameObject {

  private double radius = 10;
  private Vec2D v; // velocity
  private double maxVelocity = 200;
  private static final String IMAGE = "ball.gif";

  public Ball(double x, double y) {
    pos = new Vec2D(x, y);

    // init scene node
    Image image = new Image(Objects.requireNonNull(
        this.getClass().getClassLoader().getResourceAsStream(IMAGE)
    ));
    sceneNode = new ImageView(image);
    sceneNode.setFitWidth(radius * 2);
    sceneNode.setFitHeight(radius * 2);

    // init collider
    collider = new SphereCollider(new Vec2D(x, y), radius);

    // FIXME: set velocity when user confirm game start
    v = new Vec2D(maxVelocity, -maxVelocity);
    scaleVelocityToMax();
  }

  /**
   * Reset the ball's position and velocity
   */
  public void reset(double x, double y) {
    pos.set(x, y);
    ((SphereCollider) collider).setPos(pos, radius);

    v.set(maxVelocity, -maxVelocity);
    scaleVelocityToMax();
  }

  /**
   * @see GameObject#step
   */
  @Override
  public void step(double time) {
    pos = pos.add(v.mul(time));
    ((SphereCollider) collider).pos = pos;
    sceneNode.setX(pos.getX() - radius);
    sceneNode.setY(pos.getY() - radius);
  }

  /**
   * @see GameObject#handleCollision
   */
  @Override
  public void handleCollision(Collision collision) {
    Vec2D normal = collision.normal;
    double vMag = v.magnitude();
    double cosAngle = v.dot(normal) / vMag;
    Vec2D vProj = normal.mul(-cosAngle * vMag);
    Vec2D perp = v.add(vProj);
    Vec2D out = vProj.add(perp);
    setVelocity(out);
  }

  /**
   * Set the velocity of the ball
   * <p>
   * NOTE: the velocity is automatically scaled to have the max speed
   */
  public void setVelocity(Vec2D v) {
    this.v = v;
    scaleVelocityToMax();
  }

  /**
   * Set the max speed of the ball
   * <p>
   * NOTE: the current velocity is then updated to have the max speed
   */
  public void setMaxSpeed(double maxSpeed) {
    this.maxVelocity = maxSpeed;
    scaleVelocityToMax();
  }

  /**
   * Set the radius of the ball
   */
  public void setRadius(double radius) {
    this.radius = radius;
    ((SphereCollider) this.collider).setPos(pos, this.radius);
    sceneNode.setFitWidth(radius * 2);
    sceneNode.setFitHeight(radius * 2);
  }

  private void scaleVelocityToMax() {
    double m = this.v.magnitude();
    this.v = this.v.mul(maxVelocity / m);
  }

  /**
   * Get the radius of the ball
   */
  public double getRadius() {
    return radius;
  }
}
