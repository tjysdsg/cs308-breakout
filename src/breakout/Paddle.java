package breakout;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Paddle {
    private Vec2D pos;
    private double width = 80; // TODO: stretch image to fit width
    private double velocity = 200; // TODO: calculate real velocity (in pixels) according to screen width
    private int dir = 0;
    private ImageView sceneNode;
    public static final String IMAGE = "paddle.gif";

    public Paddle(double x, double y) {
        Image image = new Image(Objects.requireNonNull(
                this.getClass().getClassLoader().getResourceAsStream(IMAGE)
        ));
        sceneNode = new ImageView(image);
        sceneNode.setFitWidth(width);
        pos = new Vec2D(x, y);
    }

    public void translate(int dir /* -1 left, 1 right */) {
        this.dir = dir;
    }

    public void step(double time) {
        pos.add(new Vec2D(dir * velocity * time, 0));
        warp();

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

    public Node getSceneNode() {
        return sceneNode;
    }
}
