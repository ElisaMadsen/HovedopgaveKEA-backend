package com.example.hovedopgavekea.controller;

import com.example.hovedopgavekea.model.FieldOfStudy;
import com.example.hovedopgavekea.service.IFieldOfStudyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/fieldOfStudy")
public class FieldOfStudyController {

    private IFieldOfStudyService fieldOfStudyService;

    public FieldOfStudyController(IFieldOfStudyService fieldOfStudyService) {
        this.fieldOfStudyService = fieldOfStudyService;
    }

    @GetMapping("/fieldOfStudies/all")
    public ResponseEntity<Set<FieldOfStudy>> getAllFieldOfStudies() {
        return new ResponseEntity(fieldOfStudyService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/createFieldOfStudy")
    public ResponseEntity<FieldOfStudy> createFieldOfStudy(@RequestBody FieldOfStudy fieldOfStudy) {
        FieldOfStudy savedFieldOfStudy = fieldOfStudyService.save(fieldOfStudy);

        if (savedFieldOfStudy != null) {
            return new ResponseEntity<>(savedFieldOfStudy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldOfStudy> getFieldOfStudyById(@PathVariable Long id) {
        return new ResponseEntity(fieldOfStudyService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/fieldOfStudies")
    public ResponseEntity<Set<FieldOfStudy>> getFieldOfStudies(@RequestParam(required = false) String fieldOfStudy) {
        if (fieldOfStudy != null) {
            // If studieretning is provided, filter by studieretning
            return new ResponseEntity(fieldOfStudyService.findByFieldOfStudyName(fieldOfStudy), HttpStatus.OK);
        } else {
            // If no studieretning is provided, return all field of studies
            return new ResponseEntity(fieldOfStudyService.findAll(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FieldOfStudy> deleteFieldOfStudy(@PathVariable Long id) {
        fieldOfStudyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
