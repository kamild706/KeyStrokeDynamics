package pl.p32.app.statstics;

import pl.p32.app.classifiers.Classifier;
import pl.p32.app.classifiers.KNN;
import pl.p32.app.metrics.Chebyshev;
import pl.p32.app.metrics.Euclidean;
import pl.p32.app.metrics.Manhattan;
import pl.p32.app.metrics.Metric;
import pl.p32.app.model.KeyStroke;
import pl.p32.app.model.repository.KeyStrokeRepository;

import java.util.ArrayList;
import java.util.List;

public class Statistics {

    public final int K_PARAMETER = 10;
    public final Metric[] metrics = new Metric[]{new Manhattan(), new Chebyshev(), new Euclidean()};
    public final Examinator examinator = new Examinator();

    public List<ExaminationResult> statisticsForSystem() {
        KeyStrokeRepository repository = KeyStrokeRepository.getInstance();
        List<List<KeyStroke>> samples = repository.findAllByPersonAndProbe();
        List<ExaminationResult> results = new ArrayList<>();
        Classifier classifier = new KNN();

        for (int k = 1; k <= K_PARAMETER; k++) {
            for (Metric metric : metrics) {
                ExaminationResult result = examinator.measureAccuracy(classifier, metric, k, samples);
                results.add(result);
            }
        }

        return results;
    }
}
