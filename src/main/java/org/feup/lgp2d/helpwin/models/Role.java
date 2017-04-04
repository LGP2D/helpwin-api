package org.feup.lgp2d.helpwin.models;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "roles")
public class Role {
    /**
     * Properties
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
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
    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
}
