package breakout;

/**
 * A rectangle collider that has two axis pointing horizontally/vertically (no rotation)
 */
public class BlockCollider extends Collider {

  Vec2D p1; // top left
  Vec2D p2; // bottom right

  /**
   * @param p1: the coordinate of the top left corner
   * @param p2: the coordinate of the bottom right corner
   */
  BlockCollider(Vec2D p1, Vec2D p2) {
    this.p1 = p1;
    this.p2 = p2;
  }
}
