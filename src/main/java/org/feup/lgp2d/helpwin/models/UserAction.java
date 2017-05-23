package org.feup.lgp2d.helpwin.models;


import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "user_action")
@AssociationOverrides({
    @AssociationOverride(name = "pk.user", joinColumns = @JoinColumn(name = "user_id")),
    @AssociationOverride(name = "pk.action", joinColumns = @JoinColumn(name = "action_id"))
})
public class UserAction {

    private UserActionId pk = new UserActionId();
    @Column
    private boolean elected;
    @Column
    private Date createdAt;

    private EvaluationStatus evaluationStatus = new EvaluationStatus(EvaluationStatus.Status.PENDING.toString());


    public UserAction() {
    }

    @EmbeddedId
    public UserActionId getPk() {
        return pk;
    }
    public void setPk(UserActionId pk) {
        this.pk = pk;
    }

    @Transient
    public User getUser() {
        return getPk().getUser();
    }
    public void setUser(User user) {
        getPk().setUser(user);
    }

    @Transient
    public Action getAction() {
        return getPk().getAction();
    }
    public void setAction(Action action) {
        getPk().setAction(action);
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isElected() {
        return elected;
    }
    public void setElected(boolean elected) {
        this.elected = elected;
    }

    @OneToOne(cascade = CascadeType.REFRESH)
    public EvaluationStatus getEvaluationStatus() {
        return evaluationStatus;
    }
    public void setEvaluationStatus(EvaluationStatus evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserAction that = (UserAction) o;

        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null)
            return false;

        return true;
    }
    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }
}
