package com.example.project.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
public class AreaListRequestDTO {

    @Size(min = 1, message = "Request should contain at least one area")
    private List<@Valid AreaRequestDTO> areas;
}
