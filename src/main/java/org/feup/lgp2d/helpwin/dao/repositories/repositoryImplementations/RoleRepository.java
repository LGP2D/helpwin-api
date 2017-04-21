package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleRepository extends AbstractRepository<Role> {

    @Override
    public Class<Role> getDomainClass() {
        return Role.class;
    }
}
