package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.User;

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
}
