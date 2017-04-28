package org.feup.lgp2d.helpwin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@XmlRootElement
@Table(name = "institutions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Institution {
    /**
     * Properties
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @Column(unique = true)
    private String description;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    @JsonIgnore
    private String imageUrl;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "institution", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Collection<VolunteeringProposal> volunteeringProposals;
    /**
     * Constructors
     */

    public Institution(){

    }

    public Institution(int id,String description, String name, String imageUrl) {
        this.id=id;
        this.description = description;
        this.name = name;
        this.imageUrl = imageUrl;
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

    public String getName() {
        return name;
    }

    @JsonProperty("imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    public Collection<VolunteeringProposal> getVolunteeringProposals() {
        return volunteeringProposals;
    }

    /**
     * Setters
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setVolunteeringProposals(Collection<VolunteeringProposal> volunteeringProposals) {
        this.volunteeringProposals = volunteeringProposals;
    }
}