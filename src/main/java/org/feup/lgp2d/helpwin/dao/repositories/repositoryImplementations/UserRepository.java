package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.SessionUtil;
import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class UserRepository extends AbstractRepository<User> {
    @Override
    public Class<User> getDomainClass() {
        return User.class;
    }

    public User authenticateUser(User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Username and/or password are null");
        }

        if ((user.getEmail() != null && user.getEmail().isEmpty()) ||
                (user.getPassword() != null && user.getPassword().isEmpty())) {
            throw new IllegalArgumentException("Username and/or password are empty");
        }

        return super.getOne(p -> p.getEmail().contentEquals(user.getEmail()) &&
                p.getPassword().contentEquals(user.getPassword()));

    }

    public User getUserByUniqueID(String uniqueId){
        Session session = SessionUtil.getSession();

        Criteria criteria = session.createCriteria(User.class);
        User user = (User) criteria.add(Restrictions.eq("uniqueId", uniqueId))
                .uniqueResult();
        session.close();
        return user;
    }

    public void updateUser(User user){
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.update(user);
        tx.commit();
        session.close();
    }
}
