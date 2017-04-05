package org.feup.lgp2d.helpwin.dao;

import org.feup.lgp2d.helpwin.models.Role;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class RoleDAO {

    /**
     * Gets the list of goals
     * @return List<Role> - list of roles
     */
    public List<Role> getRoles() {
        Session session = SessionUtil.getSession();
        Query query = session.createQuery(" from " + Role.class.getCanonicalName());
        List<Role> roles = query.list();
        session.close();
        return roles;
    }
}
