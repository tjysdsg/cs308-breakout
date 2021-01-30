package breakout;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class GameObject {
    protected Vec2D pos;
    protected ImageView sceneNode;
    protected Collider collider;

    public Node getSceneNode() {
        return sceneNode;
    }

    public Collider getCollider() {
        return collider;
    }

    public void step(double time) {
    }

    public void setPos(Vec2D pos) {
        this.pos = pos;
    }
}
