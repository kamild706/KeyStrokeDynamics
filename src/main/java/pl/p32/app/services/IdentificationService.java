package pl.p32.app.services;

import pl.p32.app.classifiers.Classifier;
import pl.p32.app.classifiers.KNN;
import pl.p32.app.keystrokes.KeyStrokesAverager;
import pl.p32.app.metrics.Metric;
import pl.p32.app.model.KeyStroke;
import pl.p32.app.model.Person;
import pl.p32.app.model.repository.KeyStrokeRepository;

import java.util.List;

public class IdentificationService {

    private IdentificationService() {}

    public static Person identify(List<KeyStroke> keyStrokes, Metric metric, int k) {
        KeyStrokeRepository repository = KeyStrokeRepository.getInstance();
        List<List<KeyStroke>> byPersonAndProbe = repository.findAllByPersonAndProbe();
//        byPersonAndProbe.forEach(System.out::println);

        keyStrokes = KeyStrokesAverager.averageOf(keyStrokes);
        keyStrokes.sort(KeyStroke.COMPARE_KEY_VALUE);
//        System.out.println(keyStrokes);

        Classifier classifier = new KNN();
        return classifier.classify(keyStrokes, byPersonAndProbe, metric, k);
    }
}
