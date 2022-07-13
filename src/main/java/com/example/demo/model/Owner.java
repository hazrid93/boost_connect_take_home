package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "owner")
public class Owner extends BaseModel {

    @Id
    @Column(name = "owner_id")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "picture")
    private String picture;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email")
    private String email;

    @Column(name = "dateOfBirth")
    private String dateOfBirth;

    @Column(name = "phone")
    private String phone;

    @Column(name = "registerDate")
    private LocalDateTime registerDate;

    @Column(name = "updatedDate")
    private LocalDateTime updatedDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Location location;

    @JsonIgnore
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Comment> comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner)) return false;
        Owner owner = (Owner) o;
        boolean b = Objects.equals(getId(), owner.getId()) && Objects.equals(getTitle(), owner.getTitle()) && Objects.equals(getFirstName(), owner.getFirstName()) && Objects.equals(getLastName(), owner.getLastName()) && Objects.equals(getPicture(), owner.getPicture()) && Objects.equals(getGender(), owner.getGender()) && Objects.equals(getEmail(), owner.getEmail()) && Objects.equals(getDateOfBirth(), owner.getDateOfBirth()) && Objects.equals(getPhone(), owner.getPhone()) && Objects.equals(getRegisterDate(), owner.getRegisterDate()) && Objects.equals(getUpdatedDate(), owner.getUpdatedDate()) && Objects.equals(getComment(), owner.getComment());
        return b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getFirstName(), getLastName(), getPicture(), getGender(), getEmail(), getDateOfBirth(), getPhone(), getRegisterDate(), getUpdatedDate(), getComment());
    }
}
