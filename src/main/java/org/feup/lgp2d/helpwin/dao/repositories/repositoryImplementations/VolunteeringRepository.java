package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.VolunteeringProposal;

public class VolunteeringRepository extends AbstractRepository<VolunteeringProposal> {
    @Override
    public Class<VolunteeringProposal> getDomainClass() {
        return VolunteeringProposal.class;
    }
}
