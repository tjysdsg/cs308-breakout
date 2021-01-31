package breakout;

import java.util.HashMap;

public class Collider {

  /**
   * Maintain a list of the colliders that are in collision with this, so that their collision won't
   * be repeatedly registered
   */
  HashMap<Collider, Boolean> collided;

  public Collider() {
    collided = new HashMap<>();
  }

  public static Collision checkCollision(Collider a, Collider b) {
    if (a == null || b == null) {
      return null;
    }

    Collision ret = null;
    if (a instanceof SphereCollider && b instanceof SphereCollider) {
      ret = Collider.sphereAndSphere((SphereCollider) a, (SphereCollider) b);
    } else if (a instanceof SphereCollider && b instanceof BlockCollider) {
      ret = Collider.blockAndSphere((BlockCollider) b, (SphereCollider) a);
    } else if (a instanceof BlockCollider && b instanceof SphereCollider) {
      ret = Collider.blockAndSphere((BlockCollider) a, (SphereCollider) b);
    }
    return ret;
  }

  /**
   * Checks collision between two sphere colliders
   *
   * @param c1 The first collider
   * @param c2 The second collider
   * @return `Collision.pos` collision position on surface of `a` `Collision.normal` normal vector
   * pointing outwards from the surface of `a`, has a magnitude of 1
   */
  public static Collision sphereAndSphere(SphereCollider c1, SphereCollider c2) {
    Collision ret = null;
    Vec2D normal = c2.pos.minus(c1.pos);
    if (normal.magnitude() <= c1.radius + c2.radius
        && !c1.collided.getOrDefault(c2, false) && !c2.collided.getOrDefault(c1, false)) {
      ret = new Collision();
      normal.normalize();
      ret.normal = normal;
      ret.pos = c1.pos.add(normal.mul(c1.radius));

      ret.a = c1;
      ret.b = c2;

      c1.collided.put(c2, true);
      c2.collided.put(c1, true);
    }

    if (ret == null) {
      c1.collided.put(c2, false);
      c2.collided.put(c1, false);
    }
    return ret;
  }

  /**
   * Checks collision between a sphere collider and a block collider NOTE: not considering the case
   * where the center of rectangle is inside the sphere TODO: Continuous Collision Detection is
   * probably needed when powered up (ball is moving fast)
   *
   * @param c2 The first collider
   * @param c1 The second collider
   * @return `Collision.pos` collision position on surface of `a` `Collision.normal` normal vector
   * pointing outwards from the surface of `a`, has a magnitude of 1
   */
  public static Collision blockAndSphere(BlockCollider c1, SphereCollider c2) {
    Collision ret = null;
    Vec2D center = c1.p1.add(c1.p2).mul(0.5);

    Vec2D diagHalf = c1.p1.minus(c1.p2).mul(0.5);
    double diagHalfMag = diagHalf.magnitude();

    Vec2D dist = c2.pos.minus(center);
    double distMag = dist.magnitude();

    double widthHalf = Math.abs(c1.p1.getX() - center.getX());
    double heightHalf = Math.abs(c1.p1.getY() - center.getY());
    if (distMag <= diagHalfMag + c2.radius) { // broad phase
      if (Math.abs(dist.getX()) <= widthHalf && Math.abs(dist.getY()) <= heightHalf
          && !c1.collided.getOrDefault(c2, false) && !c2.collided.getOrDefault(c1, false)) {
        ret = new Collision();

        double dx = dist.getX();
        double dy = dist.getY();

        // determine which side of the block that collision occurred on
        double tan = Math.abs(dy / dx);
        double tanDiag = diagHalf.getY() / diagHalf.getX();

        if (dx < 0 && tan < tanDiag) { // left
          ret.normal = new Vec2D(-1, 0);
        } else if (dx > 0 && tan < tanDiag) { // right
          ret.normal = new Vec2D(1, 0);
        } else if (dy < 0 && tan > tanDiag) {
          ret.normal = new Vec2D(0, 1); // top
        } else if (dy > 0 && tan > tanDiag) {
          ret.normal = new Vec2D(0, 1); // bottom
        }

        // collision position
        dist.normalize();
        ret.pos = c2.pos.add(dist.mul(c2.radius));

        ret.a = c1;
        ret.b = c2;

        c1.collided.put(c2, true);
        c2.collided.put(c1, true);
      }
    }

    if (ret == null) {
      c1.collided.put(c2, false);
      c2.collided.put(c1, false);
    }
    return ret;
  }
}
