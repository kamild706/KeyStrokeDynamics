package pl.p32.app.metrics;

import pl.p32.app.model.KeyStroke;

import java.util.List;

public interface Metric {

    double distance(List<KeyStroke> x, List<KeyStroke> y);
}
