package org.feup.lgp2d.helpwin.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Date;

@Entity
@XmlRootElement
@Table(name = "volunteeringProposals")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VolunteeringProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String description;
    private String imageUrl;
    private int coins;
    private Date date;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private VolunteeringType volunteeringType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Institution institution;

    public VolunteeringProposal() {
    }
    public VolunteeringProposal(String description, String imageUrl, int coins, Date date,
                                VolunteeringType volunteeringType, Institution institution) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.coins = coins;
        this.date = date;
        this.volunteeringType = volunteeringType;
        this.institution = institution;
    }

    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public int getCoins() {
        return coins;
    }
    public Date getDate() {
        return date;
    }


    public VolunteeringType getVolunteeringType() {
        return volunteeringType;
    }

    public Institution getInstitution() {
        return institution;
    }


}
