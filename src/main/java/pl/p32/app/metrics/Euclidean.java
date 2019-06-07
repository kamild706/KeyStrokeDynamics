package pl.p32.app.metrics;

import pl.p32.app.model.KeyStroke;

import java.util.Iterator;
import java.util.List;

public class Euclidean implements Metric {

    @Override
    public double distance(List<KeyStroke> x, List<KeyStroke> y) {
        Iterator<KeyStroke> itX = x.iterator();
        Iterator<KeyStroke> itY = y.iterator();
        double sum = 0;
        while (itX.hasNext() && itY.hasNext()) {
            int dX = itX.next().getDwellTime();
            int dY = itY.next().getDwellTime();
            sum += Math.pow(dX - dY, 2);
        }
        return Math.sqrt(sum);
    }

    @Override
    public String toString() {
        return "Euclidean";
    }
}
