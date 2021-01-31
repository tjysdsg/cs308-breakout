package breakout;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SplashScreen extends UIComponent {

  private Text textWin;
  private Text textRules;

  public SplashScreen() {
    super(new Vec2D(0, 0));
  }

  @Override
  protected void buildTree() {
    textWin = new Text("YOU WON\nPress Space to load next level");
    textWin.setFont(Font.font("Arial Black", FontWeight.BOLD, FontPosture.REGULAR, 30));
    textWin.setFill(Color.BLUE);
    textWin.setStroke(Color.WHITE);
    textWin.setStrokeWidth(2.0);
    centerTextOnScreen(textWin);

    String[] ruleLines = Util.readResourceTxtToLines("rules.txt");
    String ruleString = "";
    if (ruleLines == null) {
      System.err.println("WARNING: Cannot read rules.txt");
    } else {
      ruleString = String.join("\n", ruleLines);
    }

    textRules = new Text(ruleString);
    textRules.setFont(Font.font("Arial Black", FontWeight.NORMAL, FontPosture.REGULAR, 15));
    centerTextOnScreen(textRules);

    children.add(textWin);
    children.add(textRules);
  }

  public void setShowWin(boolean showWin) {
    textWin.setVisible(showWin);
  }

  public void setShowRules(boolean showRules) {
    textRules.setVisible(showRules);
  }

  private void centerTextOnScreen(Text t) {
    t.setX(Main.SCREEN_WIDTH / 2.0 - t.getBoundsInLocal().getWidth() / 2);
    t.setY(Main.SCREEN_HEIGHT / 2.0 - t.getBoundsInLocal().getHeight() / 2);
  }
}
