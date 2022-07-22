package com.example.project.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CityRequestDTO {

    @NotBlank
    @Size(min = 3, max = 50, message = "City name length should be between 3 and 50 chars")
   private String name;

    @NotNull
    @Positive
   private Long areaId;
}
