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
public class CityListRequestDTO {

    @Size(min = 1, message = "Request should contain at least on city")
    private List<@Valid CityRequestDTO> cities;
}
