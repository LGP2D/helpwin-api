package org.feup.lgp2d.helpwin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
@Table(name = "roles")
public class Role {
    /**
     * Properties
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @Column(unique = true)
    private String description;

    /**
     * Constructors
     */
    public Role() {
    }
    public Role(int id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * Getters
     */
    @JsonProperty("id")
    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }
}
