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
@Table(name = "users")
public class User {
    /**
     * Properties
     */
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;

    @JsonIgnore
    @Column(unique = true, nullable = false)
    private String uniqueId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date birthDate;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonProperty(value = "password")
    private String password;

    @Column(nullable = false)
    private String profession;

    @Column
    @JsonIgnore
    private String imageUrl;

    @OneToOne(cascade = CascadeType.REFRESH)
    private Role role;

    @Transient
    @JsonIgnore
    private String token;

    /*@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_actions", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "action_id", nullable = false, updatable = false)
    })
    private List<Action> userActions = new ArrayList<>();*/
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user", cascade = CascadeType.ALL)
    private List<UserAction> userActions = new ArrayList<>();

    /**
     * Constructors
     */
    public User() {
    }
    public User(int id, String name, Date birthDate, String email, String password,
                String profession, String imageUrl, Role role) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.profession = profession;
        this.imageUrl = imageUrl;
        this.role = role;
    }

    /**
     * Util
     */
    public void generateUniqueId() {
        this.uniqueId = UUID.randomUUID().toString();
    }

    /**
     * Getters
     */
    @JsonIgnore
    public Integer getId() {
        return id;
    }

    @JsonProperty("uniqueId")
    public String getUniqueId() {
        return uniqueId;
    }

    @JsonProperty("imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public String getEmail() {
        return email;
    }
    public String getProfession() {
        return profession;
    }
    public Role getRole() {
        return role;
    }
    @JsonProperty("token")
    public String getToken() {
        return token;
    }
    public List<UserAction> getUserActions() {
        return userActions;
    }

    /**
     * Setters
     */
    @JsonProperty("uniqueId")
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setID(int ID){this.id = ID;}
    public void setName(String name) {
        this.name = name;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setProfession(String profession) {
        this.profession = profession;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public void setUserActions(List<UserAction> userActions) {
        this.userActions = userActions;
    }
}
