package org.feup.lgp2d.helpwin.dao;

import org.feup.lgp2d.helpwin.customExceptions.ConstraintViolationExceptionMapper;
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

    /**
     * Creates a user.
     * This will generate an uniqueId automatically for the user
     * @param bean User - the user to be created
     */
    public void addUser(User bean) {
        Session session = SessionUtil.getSession();
        try {
            Transaction tx = session.beginTransaction();
            addUser(session, bean);
            tx.commit();
        } catch (ConstraintViolationException cve) {
            throw new ConstraintViolationExceptionMapper(cve.getCause().getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Private method to create a user
     * @param session Session - the hibernate session
     * @param bean User - the model of the user
     */
    private void addUser(Session session, User bean) {
        User user = new User(bean.getId(), bean.getName(), bean.getBirthDate(), bean.getEmail(), bean.getPassword(),
                bean.getProfession(), bean.getImageUrl(), bean.getRole());

        user.generateUniqueId();

        session.save(user);
    }
}
