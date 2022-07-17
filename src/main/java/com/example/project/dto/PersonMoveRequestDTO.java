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
    Long personId;

    @Positive
    @NotNull
    Long fromCityId;

    @Positive
    @NotNull
    Long toCityId;
}
