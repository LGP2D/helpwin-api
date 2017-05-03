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


    private int id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Voucher> vouchers = new ArrayList<Voucher>();

    public Company() {
    }

    public Company(String name, String email, String password, ArrayList<Voucher> vouchers) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.vouchers = vouchers;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    @Column(nullable = false)
    @JsonProperty(value = "password")
    public String getPassword() {
        return password;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    public ArrayList<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(ArrayList<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
