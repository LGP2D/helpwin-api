package org.feup.lgp2d.helpwin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "vouchers")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;

    @Column
    private String description;

    @Column(nullable = false)
    private String imageURL;

    // TODO: Convert EAGER in LAZY
    @ManyToOne(fetch = FetchType.EAGER)
    private Company company = new Company();

    public Voucher() {}

    public Voucher(String description, String imageURL, Company company) {
        this.description = description;
        this.imageURL = imageURL;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Company getCompany() {
        return company;
    }
}
