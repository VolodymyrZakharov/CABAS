package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonRequestDTO {

    @NotBlank(message = "value can not be blanked")
    @Size(min = 2, max = 50, message = "Person name length should be between 2 and 50 chars")
    private String firstName;

    @NotBlank(message = "value can not be blanked")
    @Size(min = 2, max = 50, message = "Person last name length should be between 2 and 50 chars")
    private String lastName;

    @NotNull(message = "value can not be null")
    @Past(message = "incorrect date of birth")
    private LocalDate dateOfBirth;

    @NotBlank(message = "value can not be blanked")
    @Email(message = "incorrect email")
    @Size(min = 10, max = 50, message = "Email length should be between 10 and 50 chars")
    private String email;

    @NotBlank(message = "value can not be blanked")
    @Pattern(regexp = "^([+]?[\\s0-9]+)?(\\d{3}|[(]?[0-9]+[)])?([-]?[\\s]?[0-9])+$", message = "incorrect phone number")
    private String phone;

    @Positive
    @NotNull(message = "value can not be null")
    private Long cityId;

}
