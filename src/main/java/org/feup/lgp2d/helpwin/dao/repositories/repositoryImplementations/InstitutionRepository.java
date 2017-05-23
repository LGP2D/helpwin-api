package org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations;

import org.feup.lgp2d.helpwin.dao.SessionUtil;
import org.feup.lgp2d.helpwin.dao.repositories.AbstractRepository;
import org.feup.lgp2d.helpwin.models.Action;
import org.feup.lgp2d.helpwin.models.Institution;
import org.feup.lgp2d.helpwin.models.User;

import java.util.ArrayList;
import java.util.List;

public class InstitutionRepository extends AbstractRepository<Institution> {
    @Override
    public Class<Institution> getDomainClass() {
        return Institution.class;
    }

    public List<User> getUserProfiles(Action action){
        List<User> usersInformation = new ArrayList<>(0);

        String query = "select users.user_id, users.birthDate, users.email, users.imageUrl, users.name, users.profession, users.uniqueId " +
                "from users join user_action on users.user_id=user_action.user_id " +
                "join actions on user_action.action_id=actions.action_id " +
                "where users.role_id=3 and actions.action_id="+ action.getId() + ";";
        usersInformation = SessionUtil.getSession().createQuery(query).list();

        return usersInformation;
    }
}

