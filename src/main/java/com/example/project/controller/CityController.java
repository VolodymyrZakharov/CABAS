package com.example.project.controller;

import com.example.project.dto.CityListRequestDTO;
import com.example.project.dto.CityResponseDTO;
import com.example.project.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CityController {
    @Autowired
    private CityService cityService;

    @PostMapping("/api/cities")
    public List<CityResponseDTO> createCities(@RequestBody @Valid CityListRequestDTO request) {
        return cityService.createCities(request.getCities());
    }

    @GetMapping("/api/cities")
    public List<CityResponseDTO> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping(value = "/api/cities", params = "name")
    public CityResponseDTO getCityByName(@RequestParam String name) {
        return cityService.getCityByName(name);
    }

    @GetMapping("/api/cities/{id}")
    public CityResponseDTO getCityById(@PathVariable Long id){
        return cityService.getCityById(id);
    }
}
