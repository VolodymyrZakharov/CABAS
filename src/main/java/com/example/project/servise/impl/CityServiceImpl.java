package com.example.project.servise.impl;

import com.example.project.dto.CityRequestDTO;
import com.example.project.dto.CityResponseDTO;
import com.example.project.entity.Area;
import com.example.project.entity.City;
import com.example.project.entity.severity.SeverityStatus;
import com.example.project.repository.AreaRepository;
import com.example.project.repository.CityRepository;
import com.example.project.servise.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public List<CityResponseDTO> createCities(List<CityRequestDTO> request) {
        return request.stream().map(x -> {
                    Area area = areaRepository
                            .findById(x.getAreaId())
                            .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format("Area with id [%s] does not exist", x.getAreaId()))
                            );
                    City city = City.builder()
                            .cityName(x.getName())
                            .area(area)
                            .severityStatus(SeverityStatus.GREEN)
                            .build();

                    cityRepository.save(city);
                    return city;
                }
        ).map(this::cityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CityResponseDTO> getAllCities() {
        return cityRepository.findAll().stream().map(this::cityToDTO).collect(Collectors.toList());
    }

    @Override
    public CityResponseDTO getCityByName(String name) {
        try {
            return cityToDTO(cityRepository.findByCityNameIgnoreCase(name));
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("City with name [%s] does not exist", name));
        }
    }

    @Override
    public CityResponseDTO getCityById(long id) {
        City city = cityRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("City with id [%s] does not exist", id))
                );
        return cityToDTO(city);
    }

    private CityResponseDTO cityToDTO(City city) {
        return CityResponseDTO.builder()
                .cityId(city.getId())
                .areaId(city.getArea().getId())
                .name(city.getCityName())
                .severityStatus(city.getSeverityStatus())
                .build();
    }
}
