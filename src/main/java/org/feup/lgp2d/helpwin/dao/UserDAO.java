package org.feup.lgp2d.helpwin.dao;

import org.feup.lgp2d.helpwin.customExceptions.HibernateExceptionMapper;
import org.feup.lgp2d.helpwin.models.User;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class UserDAO {

    /**
     * Gets a list of all users
     * @return List<User> list of the users
     */
    public List<User> getAllUsers() {
        Session session = SessionUtil.getSession();
        Query query = session.createQuery(" from " + User.class.getCanonicalName());
        List<User> users = query.list();
        return users;
    }

    public void addUser(User bean) {
        Session session = SessionUtil.getSession();
        try {
            Transaction tx = session.beginTransaction();
            addUser(session, bean);
            tx.commit();
        } catch (ConstraintViolationException cve) {
            throw new HibernateExceptionMapper(cve.getCause().getMessage());
        } finally {
            session.close();
        }
    }

    private void addUser(Session session, User bean) {
        User user = new User(bean.getId(), bean.getName(), bean.getBirthDate(), bean.getEmail(), bean.getPassword(),
                bean.getProfession(), bean.getImageUrl(), bean.getRole());

        user.generateUniqueId();

        session.save(user);
    }
}
