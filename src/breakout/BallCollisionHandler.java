package breakout;

public class BallCollisionHandler {
    public static void handle(Ball ball, Collision collision, boolean powered) {
        // TODO: different angle of emergence when powered up

        Vec2D v = ball.getVelocity();
        Vec2D normal = collision.normal;
        double vMag = v.magnitude();
        double cosAngle = v.dot(normal) / vMag;
        Vec2D vProj = normal.mul(-cosAngle * vMag);
        Vec2D perp = v.add(vProj);
        Vec2D out = vProj.add(perp);
        ball.setVelocity(out);
    }
}
