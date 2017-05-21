package org.feup.lgp2d.helpwin.models;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class UserActionId implements Serializable {

    private User user;
    private Action action;


    @ManyToOne
    public User getUser() {
        return user;
    }
    @ManyToOne
    public Action getAction() {
        return action;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        UserActionId that = (UserActionId) obj;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (action != null ? !action.equals(that.action) : that.action != null)
            return false;

        return true;
    }
    public int hashCode() {
        int result;
        result = (user != null ? user.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }
}
