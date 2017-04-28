package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.Institution;

public class InstitutionRepository extends AbstractRepository<Institution> {
    @Override
    public Class<Institution> getDomainClass() {
        return Institution.class;
    }
}

