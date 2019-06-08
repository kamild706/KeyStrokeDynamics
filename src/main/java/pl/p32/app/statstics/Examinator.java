package pl.p32.app.statstics;

import pl.p32.app.classifiers.Classifier;
import pl.p32.app.metrics.Metric;
import pl.p32.app.model.KeyStroke;
import pl.p32.app.model.Person;

import java.util.ArrayList;
import java.util.List;

public class Examinator {

    public ExaminationResult measureAccuracy(Classifier classifier, Metric metric, int k, List<List<KeyStroke>> samples) {
        int successes = 0;

        for (int i = 0; i < samples.size(); i++) {
            List<KeyStroke> current = samples.get(i);
            List<List<KeyStroke>> restOfSamples = new ArrayList<>(samples);
            restOfSamples.remove(i);

            Person person = classifier.classify(current, restOfSamples, metric, k);
            Person expectedPerson = current.get(0).getPerson();
            if (person.equals(expectedPerson)) {
                successes++;
            }
        }

        return new ExaminationResult(metric.getClass().getSimpleName(), k, successes, samples.size());
    }
}
