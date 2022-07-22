package com.example.project.service;

import com.example.project.dto.AreaRequestDTO;
import com.example.project.dto.AreaResponseDTO;

import java.util.List;

public interface AreaService {

    List<AreaResponseDTO> createArea(List<AreaRequestDTO> request);

   AreaResponseDTO getAreaById(Long id);

    AreaResponseDTO getAreaByName(String name);
}
