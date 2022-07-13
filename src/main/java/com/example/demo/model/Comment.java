package com.example.demo.model;

import com.example.demo.dto.BaseDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "comment")
public class Comment extends BaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "message")
    private String message;

    @Column(name = "post")
    private String post;

    @Column(name = "publish_date")
    private LocalDateTime publishDate;

    @ManyToOne
    @JoinColumn(name="owner_id", nullable=false)
    private Owner owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", post='" + post + '\'' +
                ", publishDate=" + publishDate +
                ", owner=" + owner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(getId(), comment.getId()) && Objects.equals(getMessage(), comment.getMessage()) && Objects.equals(getPost(), comment.getPost()) && Objects.equals(getPublishDate(), comment.getPublishDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMessage(), getPost(), getPublishDate());
    }
}
