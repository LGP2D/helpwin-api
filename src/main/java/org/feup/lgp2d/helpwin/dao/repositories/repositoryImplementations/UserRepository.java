package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.User;

public class UserRepository extends AbstractRepository<User> {
    @Override
    public Class<User> getDomainClass() {
        return User.class;
    }
}
