package com.example.project.service.impl;

import com.example.project.dto.AreaRequestDTO;
import com.example.project.dto.AreaResponseDTO;
import com.example.project.entity.Area;
import com.example.project.entity.City;
import com.example.project.repository.AreaRepository;
import com.example.project.repository.CityRepository;
import com.example.project.service.AreaService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<AreaResponseDTO> createArea(List<AreaRequestDTO> request) {
        return request.stream()
                .map(x -> {
                            Area area = Area.builder()
                                    .areaName(x.getAreaName())
                                    .areaCode(x.getAreaCode())
                                    .build();
                            areaRepository.save(area);
                            return area;
                        }
                )
                .map(this::areaToDTO).collect(Collectors.toList());
    }

    @Override
    public AreaResponseDTO getAreaById(Long id) {
        Area area = areaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Area with id [%s] not found", id)));

        return areaToDTO(area);
    }

    @Override
    public AreaResponseDTO getAreaByName(String name) {
        try {
            return areaToDTO(areaRepository.findByAreaNameIgnoreCase(name));
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Area with name [%s] does not exist", name));
        }
    }

    private AreaResponseDTO areaToDTO(Area area) {
        var cityId = cityRepository.findAllByArea(area)
                .stream()
                .map(City::getId)
                .collect(Collectors.toList());

        return AreaResponseDTO.builder()
                .id(area.getId())
                .areaName(area.getAreaName())
                .areaCode(area.getAreaCode())
                .cityIds(cityId)
                .build();
    }
}
