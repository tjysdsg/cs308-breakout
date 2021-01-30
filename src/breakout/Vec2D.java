package breakout;

public class Vec2D {
    private double x = 0;
    private double y = 0;

    public Vec2D() {
    }

    public Vec2D(double x, double y) {
        this.set(x, y);
    }

    public Vec2D(Vec2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double dot(Vec2D other) {
        return x * other.x + y * other.y;
    }

    public double magnitude2() {
        return x * x + y * y;
    }

    public double magnitude() {
        return Math.sqrt(magnitude2());
    }

    public void normalize() {
        x /= magnitude();
        y /= magnitude();
    }

    public Vec2D add(Vec2D v) {
        Vec2D ret = new Vec2D(this);
        ret.x += v.x;
        ret.y += v.y;
        return ret;
    }

    public Vec2D minus(Vec2D v) {
        Vec2D ret = new Vec2D(this);
        ret.x -= v.x;
        ret.y -= v.y;
        return ret;
    }

    public Vec2D mul(Vec2D v) {
        Vec2D ret = new Vec2D(this);
        ret.x *= v.x;
        ret.y *= v.y;
        return ret;
    }

    public Vec2D mul(double v) {
        Vec2D ret = new Vec2D(this);
        ret.x *= v;
        ret.y *= v;
        return ret;
    }

    public Vec2D div(Vec2D v) {
        Vec2D ret = new Vec2D(this);
        ret.x /= v.x;
        ret.y /= v.y;
        return ret;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
