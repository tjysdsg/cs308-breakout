package breakout;

import java.util.ArrayList;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * UI components are updated only when necessary, so no `step()` method
 */
public class UIComponent {

  protected Vec2D pos;
  protected Group sceneNode;
  protected ArrayList<Node> children;

  public UIComponent(Vec2D pos) {
    this.pos = pos;
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
