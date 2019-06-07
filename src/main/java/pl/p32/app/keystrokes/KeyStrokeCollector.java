package pl.p32.app.keystrokes;

import javafx.scene.input.KeyCode;
import lombok.Getter;
import pl.p32.app.model.KeyStroke;

import java.util.ArrayList;
import java.util.List;

public class KeyStrokeCollector {

    private final String stringToType;
    @Getter private final List<KeyStroke> keystrokes;
    private final List<KeyPress> pressedKeys;
    private boolean isShiftPressed;
    private int index = 0;
    private int misspelledCharacters = 0;

    public KeyStrokeCollector(String sentence) {
        stringToType = sentence;
        keystrokes = new ArrayList<>();
        pressedKeys = new ArrayList<>();
        isShiftPressed = false;
    }

    public void onKeyPressed(KeyCode keyCode) {
        long pressedAt = System.currentTimeMillis();

        if (misspelledCharacters > 0 || index >= stringToType.length()) {
            return;
        }

        char key = convertKeyToProperCase(keyCode);
        char expectedChar = stringToType.charAt(index);

        System.out.println(key + "," + expectedChar);

        if (key == expectedChar) {
            index++;
            KeyPress keyPress = new KeyPress(key, pressedAt);
            pressedKeys.add(keyPress);
        } else {
            misspelledCharacters++;
        }
    }

    public void onBackspacePressed() {
        if (misspelledCharacters > 0)
            misspelledCharacters--;
    }

    public void onShiftPressed() {
        isShiftPressed = true;
    }

    public void onKeyReleased(KeyCode keyCode) {
        long releasedAt = System.currentTimeMillis();

        if (pressedKeys.size() > 0) {
            char key = (char) keyCode.impl_getCode();
            KeyPress keyPress = new KeyPress(key, 0);

            int index = pressedKeys.indexOf(keyPress);
            if (index == -1) {
                keyPress = new KeyPress((char) (key + 32), 0);
                index = pressedKeys.indexOf(keyPress);
            }

            if (index >= 0) {
                KeyPress recordedPress = pressedKeys.get(index);
                pressedKeys.remove(index);

                int dwellTime = (int) (releasedAt - recordedPress.timestamp);
                KeyStroke keyStroke = new KeyStroke(String.valueOf(recordedPress.key), dwellTime);
                keystrokes.add(keyStroke);

                System.out.println("added: " + recordedPress.key);
            }
        }
    }

    private char convertKeyToProperCase(KeyCode keyCode) {
        int asciiValue = keyCode.impl_getCode();
        if (keyCode.isLetterKey())
            asciiValue = isShiftPressed ? asciiValue : asciiValue + 32;

        return (char) asciiValue;
    }

    public void onShiftReleased() {
        isShiftPressed = false;
    }

    public boolean isValid() {
        return index == stringToType.length();
    }
}
