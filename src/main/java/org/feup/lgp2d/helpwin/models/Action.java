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
    private boolean valid;

    @Column(nullable=false)
    private boolean verified;

    @Column(nullable=false)
    private int availablePosition;

    @OneToOne (cascade = CascadeType.REFRESH)
    private User user;

    /*@ManyToMany(fetch = FetchType.LAZY, mappedBy = "userActions")
    private List<User> users = new ArrayList<>();*/

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.action")
    private List<UserAction> userActions = new ArrayList<>();


    /**
     * Constructors
     */

    public Action() {
    }
    public Action(int id,String type, String description, Date startDate, Date endDate, boolean valid, User user,boolean verified,int availablePosition) {
        this.id=id;
        this.type = type;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.valid = valid;
        this.user = user;
        this.verified=verified;
        this.availablePosition=availablePosition;
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

    public boolean isValid() {
        return valid;
    }

    public User getUser() {
        return user;
    }

    public boolean isVerified() {
        return verified;
    }

    public int getAvailablePosition() {
        return availablePosition;
    }

    public List<UserAction> getUserActions() {
        return userActions;
    }
}
