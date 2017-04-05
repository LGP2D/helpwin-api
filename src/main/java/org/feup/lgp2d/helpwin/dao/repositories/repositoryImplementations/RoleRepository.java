package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.Role;

public class RoleRepository extends AbstractRepository<Role> {
    @Override
    public Class<Role> getDomainClass() {
        return Role.class;
    }
}
