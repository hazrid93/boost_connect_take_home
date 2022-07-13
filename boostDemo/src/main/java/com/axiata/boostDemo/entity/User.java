package com.axiata.boostDemo.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@DynamicUpdate
@Table(name="USER")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="title")
    private String title;
    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @Column(name="picture")
    private String picture;
}