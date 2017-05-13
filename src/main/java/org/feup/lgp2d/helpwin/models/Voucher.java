package org.feup.lgp2d.helpwin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicInsert;

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

    @Column
    private String type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private String company;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private Integer quantity;

    @Column
    private Integer credits;

    /* Porque Instituion estão no Voucher?
    @ManyToOne(cascade = CascadeType.ALL)
    private Institution institution;
    */
    public Voucher() {}

    public Voucher(String description, String imagePath, String company) {
        this.description = description;
        this.imagePath = imagePath;
        this.company = company;
    }

    public Voucher(int id, String uniqueId, String type, String description, Date startDate,
                   Date endDate, Integer quantity, Integer credits /* , Institution institution */) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.type = type;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.quantity = quantity;
        this.credits = credits;
        //this.institution = institution;
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

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getCredits() {
        return credits;
    }

    /* public Institution getInstitution() {
        return institution;
    } */

    public String getImagePath() { return imagePath; }

    public String getCompany() { return company; }

    /**
     *  Utils
     */
    public void generateUniqueId(){this.uniqueId= UUID.randomUUID().toString();}
    @JsonProperty("uniqueId")
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
