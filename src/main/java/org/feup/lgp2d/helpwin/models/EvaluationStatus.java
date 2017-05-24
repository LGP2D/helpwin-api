package org.feup.lgp2d.helpwin.models;

import antlr.collections.Enumerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "evaluation_status")
public class EvaluationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;
    @Column(unique = true)
    private String description;

    public EvaluationStatus() {
    }
    public EvaluationStatus(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public enum Status {
        PENDING,
        SUCCESS,
        FAILED,
    }
}
