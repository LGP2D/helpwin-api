package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.SessionUtil;
import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.Company;
import org.feup.lgp2d.helpwin.models.User;
import org.feup.lgp2d.helpwin.models.Voucher;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CompanyRepository extends AbstractRepository<Company> {

    @Override
    public Class<Company> getDomainClass() {return Company.class;}

    public Company getUserByUniqueID(String uniqueId){
        Session session = SessionUtil.getSession();

        Criteria criteria = session.createCriteria(Company.class);
        Company company = (Company) criteria.add(Restrictions.eq("uniqueId", uniqueId))
                .uniqueResult();
        Hibernate.initialize(company.getVouchers());
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

    @Override
    public List<Company> getAll() {
        Session session = openSession();

        Criteria criteria = session.createCriteria(Company.class);

        List<Company> entities;

        try {
            entities = criteria.list();

            for (Company c : entities) {
                c.setVouchers(null);
            }

            return entities;
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        } finally {
            session.close();
        }
        return null;
    }
}
