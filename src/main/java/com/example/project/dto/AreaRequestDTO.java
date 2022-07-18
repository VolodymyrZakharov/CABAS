package com.example.project.dto;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
public class AreaRequestDTO {

    @NotBlank
    @Size(min = 3, max = 50, message = "Area name length should be between 3 and 50 chars")
   private String areaName;

    @NotBlank
    @Size(min = 2, max = 2, message = "Area code length should be 2 chars")
   private String areaCode;
}
