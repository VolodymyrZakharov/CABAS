package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonResponseDTO {

    Long id;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String email;
    String phone;
    Long cityId;
    Long areaId;
    Long guardianId;
    List<PersonChildrenResponseDTO> children;
}
