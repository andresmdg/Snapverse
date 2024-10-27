package org.codecollad.snapverse.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

import lombok.*;
import jakarta.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String body;
  
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Like> likes;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnore
  private User user;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Comment> comments;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Attachment> attachments;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

}
