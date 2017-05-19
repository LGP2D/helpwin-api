package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.SessionUtil;
import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.Company;
import org.feup.lgp2d.helpwin.models.User;
import org.feup.lgp2d.helpwin.models.Voucher;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class CompanyRepository extends AbstractRepository<Company> {

    @Override
    public Class<Company> getDomainClass() {return Company.class;}

    public Company getUserByUniqueID(String uniqueId){
        Session session = SessionUtil.getSession();

        Criteria criteria = session.createCriteria(Company.class);
        Company company = (Company) criteria.add(Restrictions.eq("uniqueId", uniqueId))
                .uniqueResult();
        session.close();
        return company;
    }

    public void updateCompany(Company company){
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.update(company);
        tx.commit();
        session.close();
    }
}
