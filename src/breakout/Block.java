package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Map;
import java.util.Objects;

// TODO: implement explosive and moving block
public class Block extends GameObject {

  public enum BlockType {
    NORMAL,
    FORTIFIED,
    EXPLOSIVE,
    INDESTRUCTIBLE,
    MOVING,
    REMOVE, // mark as removed, so `Level` can remove it from the scene
  }

  private static final Map<BlockType, String> IMAGE = Map.of(
      BlockType.NORMAL, "brick2.gif",
      BlockType.FORTIFIED, "brick8.gif",
      BlockType.EXPLOSIVE, "brick10.gif",
      BlockType.INDESTRUCTIBLE, "brick3.gif",
      BlockType.MOVING, "brick1.gif",
      BlockType.REMOVE, "brick3.gif"
  );

  private static final Map<BlockType, Integer> HEALTH = Map.of(
      BlockType.NORMAL, 1,
      BlockType.FORTIFIED, 2,
      BlockType.EXPLOSIVE, 1,
      BlockType.INDESTRUCTIBLE, 1,
      BlockType.MOVING, 1,
      BlockType.REMOVE, 1
  );

  private BlockType type;
  private int health;
  private Vec2D p1; // top left
  private Vec2D p2; // bottom right

  public Block(BlockType type, Vec2D p1, Vec2D p2) {
    this.type = type;
    this.health = HEALTH.get(this.type);
    this.p1 = p1;
    this.p2 = p2;

    // init image
    sceneNode = new ImageView();
    updateBlockImage();
    sceneNode.setFitWidth(p2.getX() - p1.getX());
    sceneNode.setFitHeight(p2.getY() - p1.getY());
    sceneNode.setX(p1.getX());
    sceneNode.setY(p1.getY());

    // init collider
    collider = new BlockCollider(this.p1, this.p2);
  }

  public void updateBlockImage() {
    Image image = new Image(Objects.requireNonNull(
        this.getClass().getClassLoader().getResourceAsStream(IMAGE.get(type))
    ));
    sceneNode.setImage(image);
  }

  @Override
  public void handleCollision(Collision collision) {
    --health;
    if (type != BlockType.INDESTRUCTIBLE && health <= 0) {
      type = BlockType.REMOVE;
    } else if (type == BlockType.FORTIFIED && health == 1) {
      type = BlockType.NORMAL;
      updateBlockImage();
    }
  }

  public BlockType getBlockType() {
    return type;
  }
}
