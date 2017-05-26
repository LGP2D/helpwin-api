package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.EvaluationStatus;
import org.feup.lgp2d.helpwin.models.UserAction;

public class UserActionRepository extends AbstractRepository<UserAction> {
    @Override
    public Class<UserAction> getDomainClass() {
        return UserAction.class;
    }

}
