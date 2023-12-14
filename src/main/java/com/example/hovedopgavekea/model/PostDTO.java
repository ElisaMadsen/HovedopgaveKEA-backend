package com.example.hovedopgavekea.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostDTO {
    private Long postId;
    private String postTitle;
    private String post;
    private String postDate;

    private Long userId;
    private String userName;

    private Long fieldOfStudyId;
    private String fieldOfStudyName;

    private int graduationYear;
    private String userEmail;
    private String userPassword;

    public PostDTO(String postId) {
        this.postId = Long.parseLong(postId);
    }

}
