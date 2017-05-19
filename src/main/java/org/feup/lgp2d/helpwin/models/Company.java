package org.feup.lgp2d.helpwin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Entity
@XmlRootElement
@Table(name = "companies")
public class Company {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column
    private String imagePath;

    @OneToMany
    Set<Voucher> vouchers;

    public Company() {
    }

    public Company(String name, String imagePath, Set<Voucher> vouchers) {
        this.name = name;
        this.imagePath = imagePath;
        this.vouchers = vouchers;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }
}
