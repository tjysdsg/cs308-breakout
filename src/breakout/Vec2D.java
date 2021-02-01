package breakout;

/**
 * 2D Vector
 */
public class Vec2D {

  private double x = 0;
  private double y = 0;

  public Vec2D() {
  }

  public Vec2D(double x, double y) {
    this.set(x, y);
  }

  /**
   * Copy constructor
   */
  public Vec2D(Vec2D other) {
    this.x = other.x;
    this.y = other.y;
  }

  /**
   * Get x coordinate
   */
  public double getX() {
    return x;
  }

  /**
   * Get y coordinate
   */
  public double getY() {
    return y;
  }

  /**
   * Set x coordinate
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Set y coordinate
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * Set x and y coordinate
   */
  public void set(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Dot product
   */
  public double dot(Vec2D other) {
    return x * other.x + y * other.y;
  }

  /**
   * Calculate the square of magnitude
   */
  public double magnitude2() {
    return x * x + y * y;
  }

  /**
   * Calculate magnitude
   */
  public double magnitude() {
    return Math.sqrt(magnitude2());
  }

  /**
   * Normalize the vector so that the magnitude is 1
   */
  public void normalize() {
    x /= magnitude();
    y /= magnitude();
  }

  /**
   * Element-wise addition
   */
  public Vec2D add(Vec2D v) {
    Vec2D ret = new Vec2D(this);
    ret.x += v.x;
    ret.y += v.y;
    return ret;
  }

  /**
   * Element-wise subtraction
   */
  public Vec2D minus(Vec2D v) {
    Vec2D ret = new Vec2D(this);
    ret.x -= v.x;
    ret.y -= v.y;
    return ret;
  }

  /**
   * Multiply by a scalar
   */
  public Vec2D mul(double v) {
    Vec2D ret = new Vec2D(this);
    ret.x *= v;
    ret.y *= v;
    return ret;
  }

  /**
   * Convert to string representation for easier printing and debugging
   */
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}
