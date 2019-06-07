package pl.p32.app.classifiers;

import pl.p32.app.metrics.Metric;
import pl.p32.app.model.KeyStroke;
import pl.p32.app.model.Person;

import java.util.List;

public interface Classifier {

    Person classify(List<KeyStroke> current, List<List<KeyStroke>> samples, Metric metric, int k);
}
