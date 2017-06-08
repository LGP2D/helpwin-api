package org.feup.lgp2d.helpwin.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@XmlRootElement
@Table(name = "actions")
public class Action {
    /**
     * Properties
     */
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "action_id")
    private Integer id;

    @JsonIgnore
    @Column(unique=true)
    private String uniqueId;

    @Column(nullable=false)
    private String type;

    @Column(nullable=false)
    private String description;

    @Column(nullable=false)
    private Date startDate;

    @Column(nullable=false)
    private Date endDate;

    @Column(nullable=false)
    @JsonIgnore
    private boolean isActive = true;

    @Column(nullable=false)
    @JsonIgnore
    private boolean verified = false;

    @Column(nullable=false)
    private int availablePosition;

    @OneToOne (cascade = CascadeType.REFRESH)
    private User user;

    @Column(nullable = false)
    private Integer credits;

    @Column(nullable = false)
    private String location;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.action")
    private List<UserAction> userActions = new ArrayList<>();


    /**
     * Constructors
     */

    public Action() {
    }
    public Action(int id, String type, String description, Date startDate, Date endDate, boolean isActive, User user, boolean verified, int availablePosition) {
        this.id=id;
        this.type = type;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.user = user;
        this.verified=verified;
        this.availablePosition=availablePosition;
    }

    public Action(String uniqueId, String type, String description, Date startDate, Date endDate, boolean isActive, boolean verified, int availablePosition, User user, Integer credits, String location) {
        this.uniqueId = uniqueId;
        this.type = type;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.verified = verified;
        this.availablePosition = availablePosition;
        this.user = user;
        this.credits = credits;
        this.location = location;
    }

    /**
     * Utils
     */

    public  void generateUniqueId(){
        this.uniqueId = UUID.randomUUID().toString();
    }

    @JsonProperty("uniqueId")
    public void setUniqueId(String uniqueId){
        this.uniqueId=uniqueId;
    }

    /**
     * Getters
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("uniqueId")
    public String getUniqueId() {
        return uniqueId;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }

    public User getUser() {
        return user;
    }

    @JsonProperty("verified")
    public boolean isVerified() {
        return verified;
    }

    public int getAvailablePosition() {
        return availablePosition;
    }

    public List<UserAction> getUserActions() {
        return userActions;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCredits() {
        return credits;
    }

    public String getLocation() {
        return location;
    }

    /**
     * Setters
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setUserActions(List<UserAction> userActions) {
        this.userActions = userActions;
    }

    public void setAvailablePosition(int availablePosition) {
        this.availablePosition = availablePosition;
    }
}
