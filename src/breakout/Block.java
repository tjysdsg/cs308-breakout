package breakout;

public class Block extends GameObject {
    public enum BlockType {
        NORMAL,
        FORTIFIED,
        EXPLOSIVE,
        INDESTRUCTIBLE,
        MOVING,
    }

    private BlockType type;
    private int health = 1;
    private Vec2D p1; // top left
    private Vec2D p2; // bottom right

    public Block(BlockType type, Vec2D p1, Vec2D p2) {
        this.type = type;
        this.p1 = p1;
        this.p2 = p2;

        collider = new BlockCollider(this.p1, this.p2);
    }
}
