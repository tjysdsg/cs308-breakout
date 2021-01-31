package breakout;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Node;

/**
 * UI components are updated only when necessary, so no `step()` method
 */
public class UIComponent {

  protected Group sceneNode;
  protected ArrayList<Node> children;

  public UIComponent() {
    children = new ArrayList<>();
    sceneNode = new Group();
  }

  public final void init() {
    // build subcomponents (used in subclass)
    buildTree();

    // add to scene
    for (Node n : children) {
      sceneNode.getChildren().add(n);
    }
  }

  protected void buildTree() {
  }

  public Node getSceneNode() {
    return sceneNode;
  }
}
