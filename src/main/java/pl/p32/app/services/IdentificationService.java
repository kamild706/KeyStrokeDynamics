package pl.p32.app.services;

import pl.p32.app.keystrokes.KeyStrokesAverager;
import pl.p32.app.classifiers.Classifier;
import pl.p32.app.classifiers.KNN;
import pl.p32.app.metrics.Metric;
import pl.p32.app.model.KeyStroke;
import pl.p32.app.model.Person;
import pl.p32.app.model.repository.KeyStrokeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class IdentificationService {

    private IdentificationService() {}

    public static Person identify(List<KeyStroke> keyStrokes, Metric metric, int k) {
        KeyStrokeRepository repository = KeyStrokeRepository.getInstance();
        List<KeyStroke> allKeyStrokes = repository.findAll();

        List<List<KeyStroke>> byPersonAndProbe = allKeyStrokes
                .stream()
                .collect(groupingBy(KeyStroke::getPerson, groupingBy(KeyStroke::getProbeId)))
                .values()
                .stream()
                .flatMap(m -> m.values().stream())
                .collect(toList());

        byPersonAndProbe.forEach(l -> l.sort(KeyStroke.COMPARE_KEY_VALUE));
        byPersonAndProbe.forEach(System.out::println);

        keyStrokes = KeyStrokesAverager.averageOf(keyStrokes);
        keyStrokes.sort(KeyStroke.COMPARE_KEY_VALUE);
        System.out.println(keyStrokes);

        Classifier classifier = new KNN();
        return classifier.classify(keyStrokes, byPersonAndProbe, metric, k);
    }
}
