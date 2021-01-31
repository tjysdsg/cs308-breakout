package breakout;

// TODO: tilted normal vector when hit at edge
public class PaddleCollider extends BlockCollider {

  PaddleCollider(Vec2D p1, Vec2D p2) {
    super(p1, p2);
  }

  public void setPos(Vec2D p1, Vec2D p2) {
    this.p1 = p1;
    this.p2 = p2;
  }
}
