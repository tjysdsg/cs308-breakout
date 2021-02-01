package breakout;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The type specification of a callback used for custom input event handling
 */
interface AxisHandler {

  void handle(double val);
}

/**
 * Keyboard input events handling and dispatch
 */
public class KeyboardInputManager implements EventHandler<KeyEvent> {

  private final Map<String, ArrayList<AxisHandler>> axisHandlers;
  private static KeyboardInputManager globalInputManager;

  /**
   * Get the global input manager that is used across the game
   */
  public static KeyboardInputManager globalInputManager() {
    if (globalInputManager == null) {
      globalInputManager = new KeyboardInputManager();
    }
    return globalInputManager;
  }

  private final HashMap<String, Double> axisValues;

  public KeyboardInputManager() {
    axisHandlers = new HashMap<>();
    axisValues = new HashMap<>();
  }

  /**
   * Handles keyboard events and dispatch to registered handlers
   */
  @Override
  public void handle(KeyEvent event) {
    KeyCode code = event.getCode();
    EventType<KeyEvent> type = event.getEventType();

    // key press
    if (type.equals(KeyEvent.KEY_PRESSED)) {
      // horizontal axis
      if (code == KeyCode.RIGHT) {
        setAxis("Horizontal", 1);
      } else if (code == KeyCode.LEFT) {
        setAxis("Horizontal", -1);
      }

      // vertical axis
      if (code == KeyCode.UP) {
        setAxis("Vertical", 1);
      } else if (code == KeyCode.DOWN) {
        setAxis("Vertical", -1);
      }

      // other axis
      setAxis(code.getName(), 1);
    }
    // key release
    else if (type.equals(KeyEvent.KEY_RELEASED)) {
      // horizontal axis
      if (code == KeyCode.RIGHT || code == KeyCode.LEFT) {
        setAxis("Horizontal", 0);
      }

      // vertical axis
      if (code == KeyCode.UP || code == KeyCode.DOWN) {
        setAxis("Vertical", 0);
      }

      // other axis
      setAxis(code.getName(), 0);
    }
  }

  /**
   * Set the value of an axis, and trigger registered callbacks
   *
   * @param axis: Axis name, such as "F", "Horizontal", "Space", etc.
   * @param val:  1 means pressed, 0 means released
   */
  public void setAxis(String axis, double val) {
    axisValues.put(axis, val);
    triggerHandler(axis, val);
  }

  /**
   * Register a callback, the callback will be called when there is an event of an axis
   *
   * @see AxisHandler
   */
  public void registerInputHandler(String axis, AxisHandler handler) {
    // FIXME: two queries
    if (axisHandlers.containsKey(axis)) {
      axisHandlers.get(axis).add(handler);
    } else {
      var handlers = new ArrayList<AxisHandler>();
      handlers.add(handler);
      axisHandlers.put(axis, handlers);
    }
  }

  private void triggerHandler(String axis, double val) {
    var handlers = axisHandlers.get(axis);
    if (handlers != null) {
      for (AxisHandler h : handlers) {
        h.handle(val);
      }
    }
  }
}
