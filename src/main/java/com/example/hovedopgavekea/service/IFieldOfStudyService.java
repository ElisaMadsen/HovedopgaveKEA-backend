package com.example.hovedopgavekea.service;

import com.example.hovedopgavekea.model.FieldOfStudy;
import com.example.hovedopgavekea.model.User;

import java.util.List;
import java.util.Optional;

public interface IFieldOfStudyService extends ICRUDService<FieldOfStudy, Long> {
    List<FieldOfStudy> findByFieldOfStudyName(String fieldOfStudyName);

}
