/*package org.feup.lgp2d.helpwin.dao.repositories;

import org.feup.lgp2d.helpwin.customExceptions.ConstraintViolationExceptionMapper;
import org.feup.lgp2d.helpwin.customExceptions.NoSuchElementExceptionMapper;
import org.feup.lgp2d.helpwin.dao.SessionUtil;
import org.feup.lgp2d.helpwin.models.Institution;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Predicate;

public class InstitutionRepositoryExample implements IRepository<Institution> {

    public InstitutionRepositoryExample() {
    }

    @Override
    public Institution get(int id) {
        return getAll()
                .stream()
                .filter(p -> p.getId() == id)
                .findAny()
                .get();
    }

    @Override
    public Institution getSingleByPredicate(Predicate<Institution> predicate) {
        try {
            return getAll()
                    .stream()
                    .filter(predicate)
                    .findAny()
                    .get();

        } catch (NoSuchElementException nsee) {
            throw new NoSuchElementExceptionMapper(nsee.getMessage());
        }
    }

    @Override
    public List<Institution> getAll() {
        Session session = SessionUtil.getSession();
        Query query = session.createQuery(" from " + Institution.class.getCanonicalName());
        List<Institution> institutions = query.list();
        session.close();
        return institutions;
    }

    @Override
    public List<Institution> get(Predicate<Institution> predicate) {
        return getAll()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public void create(Institution Institution) {
        Session session = SessionUtil.getSession();
        try {
            Transaction tx = session.beginTransaction();
            addInstitution(session, Institution);
            tx.commit();
        } catch (ConstraintViolationException cve) {
            throw new ConstraintViolationExceptionMapper(cve.getCause().getMessage());
        } finally {
            session.close();
        }
    }

    private void addInstitution(Session session, Institution institution) {
        Institution _institution = new Institution(institution.getId(), institution.getDescription(),institution.getName(),institution.getImageUrl());
        session.save(_institution);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(Institution institution) {

    }

    @Override
    public void delete(Predicate<Institution> predicate) {
        get(predicate).forEach(this::delete);
    }
}
*/