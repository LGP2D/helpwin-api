package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.EvaluationStatus;


public class EvaluationStatusRepository extends AbstractRepository<EvaluationStatus> {
    @Override
    public Class<EvaluationStatus> getDomainClass() {
        return EvaluationStatus.class;
    }

}

