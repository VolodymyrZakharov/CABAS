package com.example.project.dto;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
public class AreaRequestDTO {

    @NotBlank
    @Length (min = 3, max = 50, message = "Area name length should be between 3 and 50 chars")
    String areaName;

    @NotBlank
    @Length (min = 2, max = 2, message = "Area code length should be 2 chars")
    String areaCode;
}
