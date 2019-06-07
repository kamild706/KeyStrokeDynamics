package pl.p32.app.model.repository;

import org.hibernate.Session;
import pl.p32.app.model.Person;

import javax.persistence.Query;
import java.util.List;


public class PersonRepository extends AbstractRepository<Person> {
    private static PersonRepository instance = new PersonRepository();

    public static PersonRepository getInstance() {
        return instance;
    }

    private PersonRepository() {
        super(Person.class);
    }

    public boolean isNameAvailable(String name) {
        Session session = openSession();
        Query query = session.createQuery("SELECT P.name FROM Person P WHERE P.name =: name");
        query.setParameter("name", name);
        List result = query.getResultList();
        closeSession();
        return result.size() == 0;
    }
}
