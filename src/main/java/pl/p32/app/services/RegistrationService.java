package pl.p32.app.services;

import pl.p32.app.keystrokes.KeyStrokesAverager;
import pl.p32.app.model.KeyStroke;
import pl.p32.app.model.Person;
import pl.p32.app.model.repository.PersonRepository;

import java.util.List;

public class RegistrationService {

    @SafeVarargs
    public static Person register(String username, List<KeyStroke>... keystrokes) throws PersonNameUnavailableException {
        PersonRepository repository = PersonRepository.getInstance();
        boolean nameAvailable = repository.isNameAvailable(username);
        if (!nameAvailable) {
            throw new PersonNameUnavailableException();
        }

        Person person = new Person();
        person.setName(username);

        int probeId = 0;
        for (List<KeyStroke> ks : keystrokes) {
            List<KeyStroke> strokes = KeyStrokesAverager.averageOf(ks);
            for (KeyStroke keyStroke : strokes) {
                keyStroke.setProbeId(probeId);
                person.addKeyStroke(keyStroke);
            }
            probeId++;
        }

        repository.save(person);
        return person;
    }
}
