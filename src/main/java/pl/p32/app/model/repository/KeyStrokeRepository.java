package pl.p32.app.model.repository;

import pl.p32.app.model.KeyStroke;

import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class KeyStrokeRepository extends AbstractRepository<KeyStroke> {
    private static KeyStrokeRepository instance = new KeyStrokeRepository();

    public static KeyStrokeRepository getInstance() {
        return instance;
    }

    private KeyStrokeRepository() {
        super(KeyStroke.class);
    }

    public List<List<KeyStroke>> findAllByPersonAndProbe() {
        List<KeyStroke> allKeyStrokes = instance.findAll();
        List<List<KeyStroke>> byPersonAndProbe = allKeyStrokes
                .stream()
                .collect(groupingBy(KeyStroke::getPerson, groupingBy(KeyStroke::getProbeId)))
                .values()
                .stream()
                .flatMap(m -> m.values().stream())
                .collect(toList());

        byPersonAndProbe.forEach(l -> l.sort(KeyStroke.COMPARE_KEY_VALUE));
        return byPersonAndProbe;
    }
}
