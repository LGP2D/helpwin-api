package org.feup.lgp2d.helpwin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "vouchers")
public class Voucher {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private String company;

    public Voucher() {}

    public Voucher(String description, String imagePath, String company) {
        this.description = description;
        this.imagePath = imagePath;
        this.company = company;
    }

    public int getId() { return id; }

    public String getDescription() { return description; }

    public String getImagePath() { return imagePath; }

    public String getCompany() { return company; }
}
