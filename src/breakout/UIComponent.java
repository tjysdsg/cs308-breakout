package breakout;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Node;

/**
 * Base class of all UI components.
 * <p>
 * NOTE: UI components are updated only when necessary, so no `step()` method
 */
public class UIComponent {

  protected Group sceneNode;
  protected ArrayList<Node> children;

  public UIComponent() {
    children = new ArrayList<>();
    sceneNode = new Group();
  }

  /**
   * Init the UI, must be called right after the constructor
   */
  public final void init() {
    // build subcomponents (used in subclass)
    buildTree();

    // add to scene
    for (Node n : children) {
      sceneNode.getChildren().add(n);
    }
  }

  /**
   * Build the scene tree
   */
  protected void buildTree() {
  }

  /**
   * @see GameObject#getSceneNode
   */
  public Node getSceneNode() {
    return sceneNode;
  }
}
