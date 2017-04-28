package org.feup.lgp2d.helpwin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "institutions")
public class Institution {
    /**
     * Properties
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @Column(unique = true)
    private String description;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    @JsonIgnore
    private String imageUrl;

    /**
     * Constructors
     */

    public Institution(){

    }

    public Institution(int id,String description, String name, String imageUrl) {
        this.id=id;
        this.description = description;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    /**
     * Getters
     */
    @JsonProperty("id")
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Setters
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}