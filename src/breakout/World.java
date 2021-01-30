package breakout;

import java.util.ArrayList;

public class World {
    private enum CheatType {
        TRIGGER_EXPLOSION,
        WIDE_PADDLE,
        REMOVE_INDESTRUCTIBLE,
        ONE_HIT_FORTIFIED,
    }

    private boolean poweredUp = false;
    private int lives = 3;
    private int screenWidth;
    private int screenHeight;
    private ArrayList<Block> blocks;
    private Ball ball;
    private Paddle paddle;

    public World() {
    }

    public static World fromLevelFile(String filename) {
        return new World();
    }

    public void step(double time) {
    }

    public void checkVictory() {
    }

    public void cheat(CheatType type) {
    }

    public void checkAndHandleCollision() {
    }
}
