package breakout;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardInputManager implements EventHandler<KeyEvent> {
    private int hAxis = 0;
    private int vAxis = 0;

    @Override
    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();
        EventType<KeyEvent> type = event.getEventType();

        // key press
        if (type.equals(KeyEvent.KEY_PRESSED)) {
            // horizontal axis
            if (code == KeyCode.RIGHT) {
                hAxis = 1;
            } else if (code == KeyCode.LEFT) {
                hAxis = -1;
            }

            // vertical axis
            if (code == KeyCode.UP) {
                vAxis = 1;
            } else if (code == KeyCode.DOWN) {
                vAxis = -1;
            }
        }
        // key release
        else if (type.equals(KeyEvent.KEY_RELEASED)) {
            // horizontal axis
            if (code == KeyCode.RIGHT || code == KeyCode.LEFT) {
                hAxis = 0;
            }

            // vertical axis
            if (code == KeyCode.UP || code == KeyCode.DOWN) {
                vAxis = 0;
            }
        }
    }

    /**
     * Get input axis value, like Unity `Input.GetAxis()`
     *
     * @param name
     * @return
     */
    public int getIntAxis(String name) {
        if (name.equals("Horizontal")) {
            return hAxis;
        } else if (name.equals("Vertical")) {
            return vAxis;
        }
        return 0;
    }
}
