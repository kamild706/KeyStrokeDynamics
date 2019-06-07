package pl.p32.app.metrics;

import pl.p32.app.model.KeyStroke;

import java.util.Iterator;
import java.util.List;

public class Chebyshev implements Metric {

    @Override
    public double distance(List<KeyStroke> x, List<KeyStroke> y) {
        Iterator<KeyStroke> itX = x.iterator();
        Iterator<KeyStroke> itY = y.iterator();
        double max = 0;
        while (itX.hasNext() && itY.hasNext()) {
            int dX = itX.next().getDwellTime();
            int dY = itY.next().getDwellTime();
            double abs = Math.abs(dX - dY);
            if (abs > max) max = abs;
        }
        return max;
    }

    @Override
    public String toString() {
        return "Chebyshev";
    }
}
