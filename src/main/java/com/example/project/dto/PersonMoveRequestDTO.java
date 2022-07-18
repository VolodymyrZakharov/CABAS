package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonMoveRequestDTO {

    @Positive
    @NotNull
    private Long personId;

    @Positive
    @NotNull
    private Long fromCityId;

    @Positive
    @NotNull
    private Long toCityId;
}
