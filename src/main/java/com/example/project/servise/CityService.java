package com.example.project.servise;


import com.example.project.dto.CityRequestDTO;
import com.example.project.dto.CityResponseDTO;

import java.util.List;

public interface CityService {
    List<CityResponseDTO> createCities(List<CityRequestDTO> request);

    List<CityResponseDTO> getAllCities();

    CityResponseDTO getCityByName(String name);

    CityResponseDTO getCityById(long id);

}
