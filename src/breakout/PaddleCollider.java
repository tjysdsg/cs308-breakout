package breakout;

/**
 * Same as BlockCollider, but can move
 * <p>
 * TODO: tilted normal vector when hit at edge
 */
public class PaddleCollider extends BlockCollider {

  /**
   * @see BlockCollider#BlockCollider
   */
  PaddleCollider(Vec2D p1, Vec2D p2) {
    super(p1, p2);
  }

  /**
   * Set the position of the collider
   *
   * @see PaddleCollider#PaddleCollider
   */
  public void setPos(Vec2D p1, Vec2D p2) {
    this.p1 = p1;
    this.p2 = p2;
  }
}
