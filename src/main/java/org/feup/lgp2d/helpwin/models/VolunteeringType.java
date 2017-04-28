package org.feup.lgp2d.helpwin.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
@Table(name = "volunteeringTypes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VolunteeringType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String type;

    public VolunteeringType() {
    }
    public VolunteeringType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }
    public String getType() {
        return type;
    }
}
