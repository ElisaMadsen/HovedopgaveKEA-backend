package com.example.hovedopgavekea.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    private String post;
    private String postDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private User user;
}
