package pl.p32.app.model.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public abstract class AbstractRepository<T> implements RepositoryInterface<T> {

    private Class<T> entity;
    private static SessionFactory factory = null;
    private Session session = null;

    protected Session openSession() {
        if (session == null)
            session = factory.openSession();
        return session;
    }

    public Session getSession() {
        return session;
    }

    protected void closeSession() {
        session.close();
        session = null;
    }

    AbstractRepository(Class<T> entityClass) {
        if (factory == null)
            factory = new Configuration().configure().buildSessionFactory();
        entity = entityClass;
    }

    public static void closeFactory() {
        factory.close();
    }

    public void save(T t) {
        openSession();
        session.beginTransaction();
        session.persist(t);
        session.flush();
        session.getTransaction().commit();
        closeSession();
    }

    public T update(T t) {
        openSession();
        session.beginTransaction();
        T tmp = (T) session.merge(t);
        session.flush();
        session.getTransaction().commit();
        closeSession();
        return tmp;
    }

    public void remove(T t) {
        openSession();
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        closeSession();
    }

    public List<T> findAll() {
        openSession();
        CriteriaQuery criteria = session.getCriteriaBuilder().createQuery();
        criteria.select(criteria.from(entity));
        List<T> list = (List<T>) session.createQuery(criteria).getResultList();
        closeSession();
        return list;
    }
}
