package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * NOTE: pos is the center of the ball, while the coordinate of ImageView is the top left corner of the image
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
    }

    @Override
    public void step(double time) {
        pos = pos.add(v.mul(time));
        ((SphereCollider) collider).pos = pos;
        sceneNode.setX(pos.getX() - radius);
        sceneNode.setY(pos.getY() - radius);
    }

    @Override
    public void handleCollision(Collision collision, boolean powered) {
        Vec2D normal = collision.normal;
        double vMag = v.magnitude();
        double cosAngle = v.dot(normal) / vMag;
        Vec2D vProj = normal.mul(-cosAngle * vMag);
        Vec2D perp = v.add(vProj);
        Vec2D out = vProj.add(perp);
        setVelocity(out);
    }

    public Vec2D getVelocity() {
        return v;
    }

    public void setVelocity(Vec2D v) {
        this.v = v;
        double m = this.v.magnitude();
        if (m > maxVelocity) {
            this.v = this.v.mul(maxVelocity / m);
        }
    }

    public double getRadius() {
        return radius;
    }
}
