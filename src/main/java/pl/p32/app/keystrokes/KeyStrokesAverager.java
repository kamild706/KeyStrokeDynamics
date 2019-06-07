package pl.p32.app.keystrokes;

import pl.p32.app.model.KeyStroke;

import java.util.*;

import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.groupingBy;

public class KeyStrokesAverager {

    @SafeVarargs
    public static List<KeyStroke> averageOf(List<KeyStroke>... sets) {
        Map<String, Double> map = Arrays.stream(sets)
                .flatMap(Collection::stream)
                .collect(groupingBy(
                        KeyStroke::getKeyValue,
                        averagingInt(KeyStroke::getDwellTime)));

        List<KeyStroke> keyStrokes = new ArrayList<>();
        map.forEach((key, dwellTime) -> {
            KeyStroke keyStroke = new KeyStroke();
            keyStroke.setDwellTime(dwellTime.intValue());
            keyStroke.setKeyValue(key);

            keyStrokes.add(keyStroke);
        });

        return keyStrokes;
    }
}
