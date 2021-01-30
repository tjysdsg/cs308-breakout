package breakout;

public class Block {
    private enum BlockType {
        NORMAL,
        FORTIFIED,
        EXPLOSIVE,
        INDESTRUCTIBLE,
        MOVING,
    }

    private BlockType type = BlockType.NORMAL;
    private int health = 1;
    private Vec2D pos;
}
