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
public class PersonUpdateRequestDTO {

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
    @Pattern(regexp = "^(\\\\+\\\\d{1,3}( )?)?((\\\\(\\\\d{1,3}\\\\))|\\\\d{1,3})[- .]?\\\\d{3,4}[- .]?\\\\d{4}$", message = "incorrect phone number")
    private String phone;
}
