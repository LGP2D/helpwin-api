package org.feup.lgp2d.helpwin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@XmlRootElement
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonProperty(value = "password")
    private String password;

    private ArrayList<Voucher> vouchers = new ArrayList<Voucher>();

    public Company() {}

    public Company(String name, String email, String password, ArrayList<Voucher> vouchers) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.vouchers = vouchers;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "company")
    public ArrayList<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(ArrayList<Voucher> vouchers) {
        this.vouchers = vouchers;
    }
}
