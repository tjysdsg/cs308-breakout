package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Paddle extends GameObject {

  private double width = 80;
  private final double height = 10;
  private final double velocity = 250;
  private int dir = 0;
  private static final String IMAGE = "paddle.gif";

  public Paddle() {
    Image image = new Image(Objects.requireNonNull(
        this.getClass().getClassLoader().getResourceAsStream(IMAGE)
    ));
    sceneNode = new ImageView(image);
    sceneNode.setFitWidth(width);
    sceneNode.setFitHeight(height);

    pos = new Vec2D(0, 0);
    collider = new PaddleCollider(pos, pos.add(new Vec2D(width, height)));
    reset();
  }

  public void translate(int dir /* -1 left, 1 right */) {
    this.dir = dir;
  }

  public void reset() {
    pos.set((Main.SCREEN_WIDTH - width) / 2.0, Main.SCREEN_HEIGHT - 20);
    ((PaddleCollider) collider).setPos(pos, pos.add(new Vec2D(width, height)));
    dir = 0;
  }

  public void setWidth(double width) {
    this.width = width;
    ((PaddleCollider) collider).setPos(pos, pos.add(new Vec2D(width, height)));
    sceneNode.setFitWidth(width);
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
