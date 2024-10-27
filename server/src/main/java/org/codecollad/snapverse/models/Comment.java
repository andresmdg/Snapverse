package org.codecollad.snapverse.models;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import jakarta.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String body;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;

  @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Like> likes;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

}
