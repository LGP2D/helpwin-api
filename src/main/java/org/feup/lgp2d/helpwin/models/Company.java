package org.feup.lgp2d.helpwin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="id")
    Set<Voucher> vouchers;

    @Column(unique = true)
    private String uniqueId;

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

    public String getUniqueId() {
        return uniqueId;
    }

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public void generateUniqueId(){this.uniqueId= UUID.randomUUID().toString();}

    public boolean addVoucher(Voucher voucher) {
        if(vouchers == null){
            vouchers = new HashSet<>(0);
        }

        if (vouchers.add(voucher)) {
            return true;
        } else return false;
    }
}
