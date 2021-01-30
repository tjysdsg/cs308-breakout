package breakout;

public class Vec2D {
    private double x = 0;
    private double y = 0;

    public Vec2D() {
    }

    public Vec2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vec2D add(Vec2D v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vec2D minus(Vec2D v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vec2D mul(Vec2D v) {
        this.x *= v.x;
        this.y *= v.y;
        return this;
    }

    public Vec2D div(Vec2D v) {
        this.x /= v.x;
        this.y /= v.y;
        return this;
    }
}
