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

    Long id;
    String areaName;
    String areaCode;
    List<Long> cityIds;
}
