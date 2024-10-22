package org.codecollad.snapverse.models;

import java.util.List;
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
  private boolean liked;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Attachment> attachments;

  
  @PrePersist
  protected void onCreate () {
    this.createdAt = LocalDateTime.now();
  }

}
