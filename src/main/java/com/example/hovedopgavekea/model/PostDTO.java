package com.example.hovedopgavekea.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PostDTO {
    private Long postId;
    private String postTitle;
    private String post;
    private String postDate;

    private Long userId;
    private String userName;
    private String fieldOfStudy;
    private int graduationYear;
    private String userEmail;
    private String userPassword;
}
