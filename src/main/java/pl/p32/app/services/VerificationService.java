package pl.p32.app.services;

import pl.p32.app.metrics.Metric;
import pl.p32.app.model.KeyStroke;
import pl.p32.app.model.Person;

import java.util.List;

public class VerificationService {

    private VerificationService() {}

    public static boolean verifyPerson(Person person, List<KeyStroke> keyStrokes, Metric metric, int k) {
        Person identifiedPerson = IdentificationService.identify(keyStrokes, metric, k);
        if (identifiedPerson == null) return false;
        return person.equals(identifiedPerson);
    }
}
