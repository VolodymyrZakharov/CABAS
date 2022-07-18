package com.example.project.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PersonListRequestDTO {

    @Size(min = 1, message ="request should contain at least on person")
    private List<@Valid PersonRequestDTO> persons;
}
