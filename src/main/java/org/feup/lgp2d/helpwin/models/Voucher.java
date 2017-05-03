package org.feup.lgp2d.helpwin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "vouchers")
public class Voucher {


    private int id;
    private String description;
    private String imageURL;
    private Company company = new Company();

    public Voucher() {
    }

    public Voucher(String description, String imageURL, Company company) {
        company = new Company();
        this.description = description;
        this.imageURL = imageURL;
        this.company = company;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    @Column(nullable = false)
    public String getImageURL() {
        return imageURL;
    }

    // TODO: Convert EAGER in LAZY
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(table = "companies", name="id", nullable = false)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
