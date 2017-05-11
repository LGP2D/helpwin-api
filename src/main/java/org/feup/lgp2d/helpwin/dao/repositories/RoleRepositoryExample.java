//package org.feup.lgp2d.helpwin.dao.repositories;
//
//import org.feup.lgp2d.helpwin.customExceptions.ConstraintViolationExceptionMapper;
//import org.feup.lgp2d.helpwin.customExceptions.NoSuchElementExceptionMapper;
//import org.feup.lgp2d.helpwin.dao.SessionUtil;
//import org.feup.lgp2d.helpwin.models.Role;
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.exception.ConstraintViolationException;
//
//import java.util.*;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//
//public class RoleRepositoryExample implements Repository<Role> {
//
//    public RoleRepositoryExample() {
//    }
//
//    @Override
//    public Role get(int id) {
//        return getAll()
//                .stream()
//                .filter(p -> p.getId() == id)
//                .findAny()
//                .get();
//    }
//
//    @Override
//    public Role getSingleByPredicate(Predicate<Role> predicate) {
//        try {
//            return getAll()
//                    .stream()
//                    .filter(predicate)
//                    .findAny()
//                    .get();
//
//        } catch (NoSuchElementException nsee) {
//            throw new NoSuchElementExceptionMapper(nsee.getMessage());
//        }
//    }
//
//    @Override
//    public List<Role> getAll() {
//        Session session = SessionUtil.getSession();
//        Query query = session.createQuery(" from " + Role.class.getCanonicalName());
//        List<Role> roles = query.list();
//        session.close();
//        return roles;
//    }
//
//    @Override
//    public List<Role> get(Predicate<Role> predicate) {
//        return getAll()
//                .stream()
//                .filter(predicate)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void create(Role role) {
//        Session session = SessionUtil.getSession();
//        try {
//            Transaction tx = session.beginTransaction();
//            addRole(session, role);
//            tx.commit();
//        } catch (ConstraintViolationException cve) {
//            throw new ConstraintViolationExceptionMapper(cve.getCause().getMessage());
//        } finally {
//            session.close();
//        }
//    }
//
//    private void addRole(Session session, Role role) {
//        Role role1 = new Role(role.getId(), role.getDescription());
//        session.save(role1);
//    }
//
//    @Override
//    public void delete(int id) {
//
//    }
//
//    @Override
//    public void delete(Role role) {
//
//    }
//
//    @Override
//    public void delete(Predicate<Role> predicate) {
//        get(predicate).forEach(this::delete);
//    }
//}
