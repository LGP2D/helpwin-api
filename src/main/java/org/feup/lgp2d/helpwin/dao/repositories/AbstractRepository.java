package org.feup.lgp2d.helpwin.dao.repositories;

import org.feup.lgp2d.helpwin.customExceptions.RuntimeExceptionMapper;
import org.feup.lgp2d.helpwin.dao.SessionUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractRepository<T> implements IRepository<T> {

    /**
     * Entity class
     * @return Class of Repository's Entity
     */
    public abstract Class<T> getDomainClass();

    /**
     * Opens the hibernate Session from SessionUtil
     * @return Session - the hibernate session
     */
    public Session openSession() {
        Session session = SessionUtil.getSession();
        return session;
    }

    /**
     * Gets all the records from a specific type
     * @return T - type of the records
     */
    public List<T> getAll() {
        Session session = openSession();

        Class<T> classType = getDomainClass();
        Criteria criteria = session.createCriteria(classType);

        List<T> entities;

        try {
            entities = criteria.list();
            return entities;
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * Find a record based on its id
     * @param id - the id of the record
     * @return T - the record
     */
    @SuppressWarnings("unchecked")
    public T getOne(int id) {
        Class<T> classType = getDomainClass();
        T entity = null;

        Session session = openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            entity = (T) session.get(classType, id);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            tx.rollback();
        } finally {
            session.close();
        }

        return entity;
    }

    /**
     * Saves or updates a record from a specific type
     * @param entity - the Entity to be saved
     * @return T - the new entity, either updated or created
     *
     * TEST: <S extends T> S (S entity) Detaching!!
     */
    public T create(T entity) {
        Assert.notNull(entity);

        Session session = openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            return entity;
        } catch (RuntimeException e) {
            tx.rollback();
            if (e instanceof ConstraintViolationException) {
                throw new RuntimeExceptionMapper(e.getCause().getMessage());
            }
            throw new RuntimeExceptionMapper(e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Delete a record based on its id
     * @param id - the record id
     * @throws RuntimeException
     */
    public void delete(int id) throws RuntimeException {
        T entity = getOne(id);

        Assert.notNull(entity);

        delete(entity);
    }

    /**
     * Delete a record based on its model
     * @param entity - the model of the record
     */
    public void delete(T entity) {
        Assert.notNull(entity);

        Session session = openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw new RuntimeExceptionMapper(e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * TESTING REGION
     */
    public T getOne(Predicate<T> predicate) {
        return getAll()
                .stream()
                .filter(predicate)
                .findAny()
                .get();
    }
}
