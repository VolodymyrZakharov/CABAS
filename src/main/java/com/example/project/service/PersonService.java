package com.example.project.service;

import com.example.project.dto.*;

import java.util.List;

public interface PersonService {

    List<PersonResponseDTO> createPersons(List<PersonRequestDTO> request);

    void updatePerson(Long id, PersonUpdateRequestDTO request);

    PersonResponseDTO postGuardian(Long personId, Long guardianId);

    PersonResponseDTO changeGuardian(GuardianChangeRequestDTO request);

    PersonResponseDTO getPersonById(Long id);

    PersonResponseDTO getPersonByEmail(String email);

    PersonResponseDTO movePerson(PersonMoveRequestDTO request);
}
