package com.example.project.servise;

import com.example.project.dto.AreaRequestDTO;
import com.example.project.dto.AreaResponseDTO;

import java.util.List;

public interface AreaService {

    List<AreaResponseDTO> createArea(List<AreaRequestDTO> request);

    List<AreaResponseDTO> getAllAreas();

    AreaResponseDTO getAreaByName(String name);
}
