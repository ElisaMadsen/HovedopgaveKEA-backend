package com.example.hovedopgavekea.service;

import com.example.hovedopgavekea.model.FieldOfStudy;
import com.example.hovedopgavekea.model.Post;
import com.example.hovedopgavekea.repository.FieldOfStudyRepository;
import com.example.hovedopgavekea.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class FieldOfStudyService implements IFieldOfStudyService{

    private FieldOfStudyRepository fieldOfStudyRepository;

    public FieldOfStudyService(FieldOfStudyRepository fieldOfStudyRepository){
        this.fieldOfStudyRepository = fieldOfStudyRepository;
    }

    @Override
    public Set<FieldOfStudy> findAll() {
        Set<FieldOfStudy> fieldOfStudies = new HashSet<>();
        fieldOfStudyRepository.findAll().forEach(fieldOfStudies::add);
        return fieldOfStudies;
    }

    @Override
    public FieldOfStudy save(FieldOfStudy object) {
        fieldOfStudyRepository.save(object);
        return object;
    }

    @Override
    public void delete(FieldOfStudy object) {
        fieldOfStudyRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        fieldOfStudyRepository.deleteById(aLong);
    }

    @Override
    public Optional<FieldOfStudy> findById(Long aLong) {
        return fieldOfStudyRepository.findById(aLong);
    }
}
