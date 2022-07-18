package com.example.project.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AreaResponseDTO {

    private Long id;
    private String areaName;
    private String areaCode;
    private List<Long> cityIds;
}
