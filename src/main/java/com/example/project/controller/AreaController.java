package com.example.project.controller;

import com.example.project.dto.AreaRequestDTO;
import com.example.project.dto.AreaResponseDTO;
import com.example.project.servise.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AreaController {

    @Autowired
    private AreaService areaService;

    @PostMapping("/api/areas")
    public List<AreaResponseDTO> createAreas(@RequestBody @Valid List<AreaRequestDTO> request) {
        return areaService.createArea(request);
    }

    @GetMapping("/api/areas")
    public List<AreaResponseDTO> getAllAreas() {
        return areaService.getAllAreas();
    }

    @GetMapping(value = "/api/areas", params = "name")
    public AreaResponseDTO getAreaByName(@RequestParam String name) {
        return areaService.getAreaByName(name);
    }
}
