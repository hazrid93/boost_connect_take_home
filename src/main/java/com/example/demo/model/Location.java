package com.example.demo.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.TimeZone;

@Entity
@Table(name = "Location")
public class Location extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "timezone")
    private TimeZone timezone;

    @OneToOne(mappedBy = "location")
    private Owner owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public TimeZone getTimezone() {
        return timezone;
    }

    public void setTimezone(TimeZone timezone) {
        this.timezone = timezone;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", timezone=" + timezone +
                ", owner=" + owner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return Objects.equals(getId(), location.getId()) && Objects.equals(getStreet(), location.getStreet()) && Objects.equals(getCity(), location.getCity()) && Objects.equals(getState(), location.getState()) && Objects.equals(getCountry(), location.getCountry()) && Objects.equals(getTimezone(), location.getTimezone()) && Objects.equals(getOwner(), location.getOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStreet(), getCity(), getState(), getCountry(), getTimezone(), getOwner());
    }
}
