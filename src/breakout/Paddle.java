package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Paddle extends GameObject {
    private double width = 80;
    private double height = 10;
    private double velocity = 200; // TODO: calculate real velocity (in pixels) according to screen width
    private int dir = 0;
    private static final String IMAGE = "paddle.gif";

    public Paddle(double x, double y) {
        Image image = new Image(Objects.requireNonNull(
                this.getClass().getClassLoader().getResourceAsStream(IMAGE)
        ));
        sceneNode = new ImageView(image);
        sceneNode.setFitWidth(width);
        sceneNode.setFitHeight(height);
        pos = new Vec2D(x, y);
        collider = new PaddleCollider(pos, pos.add(new Vec2D(width, height)));
    }

    public void translate(int dir /* -1 left, 1 right */) {
        this.dir = dir;
    }

    @Override
    public void step(double time) {
        pos = pos.add(new Vec2D(dir * velocity * time, 0));
        warp();

        // update collider
        ((PaddleCollider) collider).setPos(pos, pos.add(new Vec2D(width, height)));

        sceneNode.setX(pos.getX());
        sceneNode.setY(pos.getY());
    }

    private void warp() {
        // TODO: implement effect of splitting paddle into two
        double maxX = Main.SCREEN_WIDTH;
        double minX = -width;
        double offset = pos.getX() - maxX;
        if (offset > 0) {
            pos.setX(-width + offset);
        }
        offset = minX - pos.getX();
        if (offset > 0) {
            pos.setX(maxX - offset);
        }
    }
}
