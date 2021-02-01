package breakout;

/**
 * Collider with a circle shape
 */
public class SphereCollider extends Collider {

  public double radius;
  public Vec2D pos;

  public SphereCollider(Vec2D pos, double radius) {
    setPos(pos, radius);
  }

  public void setPos(Vec2D pos, double radius) {
    this.pos = pos;
    this.radius = radius;
  }
}
