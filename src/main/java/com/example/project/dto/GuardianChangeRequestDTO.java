package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GuardianChangeRequestDTO {
    @Positive
    @NotNull
    Long fromGuardian;

    @Positive
    @NotNull
    Long toGuardian;

    @Positive()
    @NotNull
    List<Long> childrenId;
}
