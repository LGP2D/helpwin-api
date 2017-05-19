package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.Company;
import org.feup.lgp2d.helpwin.models.Voucher;

public class CompanyRepository extends AbstractRepository<Company> {

    @Override
    public Class<Company> getDomainClass() {return Company.class;}
}
