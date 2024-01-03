package com.example.hovedopgavekea.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String postTitle;

    @Column(columnDefinition = "TEXT")
    private String post;
    private String postDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Comment> comments = new HashSet<>();
}



