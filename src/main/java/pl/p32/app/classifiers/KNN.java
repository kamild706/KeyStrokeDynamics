package pl.p32.app.classifiers;

import pl.p32.app.metrics.Metric;
import pl.p32.app.model.KeyStroke;
import pl.p32.app.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Long.compare;
import static java.util.stream.Collectors.*;

public class KNN implements Classifier {

    @Override
    public Person classify(List<KeyStroke> current, List<List<KeyStroke>> samples, Metric metric, int k) {
        List<Pair<Person, Double>> results = new ArrayList<>();

        samples.forEach(list -> {
            double distance = metric.distance(current, list);
            Person person = list.get(0).getPerson();
            Pair<Person, Double> pair = new Pair<>(person, distance);
            results.add(pair);
        });

        Map<Person, Long> counted = results.stream()
                .sorted((o1, o2) -> (int) (o1.second - o2.second))
                .limit(k)
                .collect(groupingBy(
                        (Pair<Person, Double> o) -> o.first,
                        counting()));

        List<Map.Entry<Person, Long>> sorted = counted.entrySet().stream()
                .sorted((e1, e2) -> compare(e2.getValue(), e1.getValue()))
                .collect(toList());
        System.out.println(sorted);
        return sorted.get(0).getKey();
    }
}
