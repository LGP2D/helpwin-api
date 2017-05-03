package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.Action;

public class ActionRepository extends AbstractRepository<Action>{
    @Override
    public Class<Action> getDomainClass() {return Action.class;}
}
