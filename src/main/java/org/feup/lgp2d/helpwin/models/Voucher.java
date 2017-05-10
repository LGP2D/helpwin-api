package org.feup.lgp2d.helpwin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.util.UUID;

@Entity
@XmlRootElement
@Table(name = "vouchers")
public class Voucher {
    /**
     * Properties
     */
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonIgnore
    @Column(unique = true)
    private String uniqueId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int credits;

    @ManyToOne(cascade = CascadeType.ALL)
    private Institution institution;

    /**
     *
     */

    public Voucher() {

    }

    public Voucher(int id, String uniqueId, String type, String description, Date startDate, Date endDate, int quantity, int credits, Institution institution) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.type = type;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.quantity = quantity;
        this.credits = credits;
        this.institution = institution;
    }

    /**
     *  Utils
     */

    public void generateUniqueId(){this.uniqueId= UUID.randomUUID().toString();}
    @JsonProperty("uniqueId")
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * Getters
     */
    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("uniqueid")
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

    public int getQuantity() {
        return quantity;
    }

    public int getCredits() {
        return credits;
    }

    public Institution getInstitution() {
        return institution;
    }

}
