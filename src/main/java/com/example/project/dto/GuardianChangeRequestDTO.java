package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GuardianChangeRequestDTO {
    @Positive
    @NotNull
    private Long fromGuardian;

    @Positive
    @NotNull
    private Long toGuardian;

    @Positive()
    @NotNull
    @Size(min = 1, message = "Guardian must have at least on child")
    private List<Long> childrenId;
}
