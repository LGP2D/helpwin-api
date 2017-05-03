package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.User;
import org.feup.lgp2d.helpwin.models.Voucher;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;


public class VoucherRepository extends AbstractRepository<Voucher> {
    @Override
    public Class<Voucher> getDomainClass() {
        return Voucher.class;
    }

    @Override
    public List<Voucher> getAll() {
        try {
            List<Voucher> vouchers;
            Session session = openSession();

            vouchers = session.createCriteria(Voucher.class).addOrder(Order.asc("id")).list();
            for (Voucher voucher : vouchers) {
                Hibernate.initialize(voucher.getCompany());
            }

            if (session.isOpen()) {
                session.close();
            }

            return vouchers;
        } catch (Exception e) {
            if (openSession().isOpen()) {
                openSession().close();
            }
            return null;
        }


    }
}
