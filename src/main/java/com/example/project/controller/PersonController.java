package com.example.project.controller;

import com.example.project.dto.*;
import com.example.project.servise.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/api/persons")
    public List<PersonResponseDTO> createPersons(@RequestBody @Valid List<PersonRequestDTO> request) {
        return personService.createPersons(request);
    }

    @PutMapping("/api/persons/{id}")
    public void updatePerson(@PathVariable Long id, @RequestBody @Valid PersonUpdateRequestDTO request) {
        personService.updatePerson(id, request);
    }

    @PostMapping("/api/persons/{personId}/guardians/{guardianId}")
    public PersonResponseDTO postGuardian(@PathVariable Long personId, @PathVariable Long guardianId) {
        return personService.postGuardian(personId, guardianId);
    }

    @PatchMapping("/api/persons/guardians")
    public PersonResponseDTO changeGuardian(@RequestBody GuardianChangeRequestDTO request) {
        return personService.changeGuardian(request);
    }

    @GetMapping("/api/persons/{id}")
    public PersonResponseDTO getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @GetMapping("/api/persons")
    public PersonResponseDTO getPersonByEmail(@RequestParam String email) {
        return personService.getPersonByEmail(email);
    }

    @PostMapping("/api/people/move")
    public PersonResponseDTO movePerson(@RequestBody @Valid PersonMoveRequestDTO request) {
        return personService.movePerson(request);
    }

}
