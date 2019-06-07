package pl.p32.app.model.repository;

import org.hibernate.Session;
import pl.p32.app.model.KeyStroke;

import javax.persistence.Query;
import java.util.List;

public class KeyStrokeRepository extends AbstractRepository<KeyStroke> {
    private static KeyStrokeRepository instance = new KeyStrokeRepository();

    public static KeyStrokeRepository getInstance() {
        return instance;
    }

    private KeyStrokeRepository() {
        super(KeyStroke.class);
    }

    /*public List<List<KeyStroke>> findByPersonAndProbe() {
        Session session = openSession();
        Query query = session.createQuery()
    }*/
}
