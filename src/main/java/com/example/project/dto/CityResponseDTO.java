package com.example.project.dto;

import com.example.project.entity.severity.SeverityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CityResponseDTO {
    Long cityId;
    String name;
    Long areaId;
    SeverityStatus severityStatus;
}
