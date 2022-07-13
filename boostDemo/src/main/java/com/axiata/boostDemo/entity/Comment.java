package com.axiata.boostDemo.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@DynamicUpdate
@Table(name="COMMENT")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="message")
    private String message;
    @Column(name="ownerId")
    private String ownerId;
    @Column(name="post")
    private String post;
    @Column(name="publishDate")
    private Date publishDate;
}