package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonChildrenResponseDTO {
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String email;
    String phone;
    Long cityId;
    Long areaId;
    Long guardianId;
}
