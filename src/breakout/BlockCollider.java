package breakout;

/**
 * A rectangle collider that has two axis pointing horizontally/vertically (no rotation)
 */
public class BlockCollider extends Collider {
    Vec2D p1; // top left
    Vec2D p2; // bottom right

    BlockCollider(Vec2D p1, Vec2D p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
}
