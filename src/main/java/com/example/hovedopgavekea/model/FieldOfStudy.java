package com.example.hovedopgavekea.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FieldOfStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fieldOfStudyId;
    private String fieldOfStudyName;

    @OneToMany(mappedBy = "fieldOfStudy", cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

    public FieldOfStudy(String fieldOfStudyId) {
        this.fieldOfStudyId = Long.parseLong(fieldOfStudyId);
    }

}
