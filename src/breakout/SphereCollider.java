package breakout;

public class SphereCollider extends Collider {
    public double radius;
    public Vec2D pos;

    SphereCollider(Vec2D pos, double radius) {
        this.pos = pos;
        this.radius = radius;
    }
}
