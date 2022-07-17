package com.example.project.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CityRequestDTO {

    @NotBlank
    @Length(min = 3, max = 50, message = "City name length should be between 3 and 50 chars")
    String name;

    @NotNull
    @Positive
    Long areaId;
}
