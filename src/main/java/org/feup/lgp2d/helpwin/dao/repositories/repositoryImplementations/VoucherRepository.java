package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.User;
import org.feup.lgp2d.helpwin.models.Voucher;


public class VoucherRepository extends AbstractRepository<Voucher> {
    @Override
    public Class<Voucher> getDomainClass() {
        return Voucher.class;
    }
}
