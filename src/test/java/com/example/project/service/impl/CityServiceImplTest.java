package com.example.project.service.impl;

import com.example.project.dto.CityRequestDTO;
import com.example.project.dto.CityResponseDTO;
import com.example.project.entity.Area;
import com.example.project.entity.City;
import com.example.project.entity.severity.SeverityStatus;
import com.example.project.repository.AreaRepository;
import com.example.project.repository.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CityServiceImplTest {

    @Mock
    CityRepository cityRepository;

    @Mock
    AreaRepository areaRepository;

    @InjectMocks
    CityServiceImpl cityService;

    Area area = Area.builder()
            .id(1L)
            .areaName("areaName")
            .areaCode("AC")
            .build();

    CityRequestDTO cityRequestDTO = CityRequestDTO.builder()
            .name("name")
            .areaId(area.getId())
            .build();

    City city = City.builder()
            .id(2L)
            .cityName(cityRequestDTO.getName())
            .severityStatus(SeverityStatus.GREEN)
            .area(area)
            .build();

    @Nested
    public class createCitiesTest {

        @Test
        @DisplayName("should save city")
        public void shouldSaveCityTest() {

            Mockito
                    .when(areaRepository.findById(area.getId()))
                    .thenReturn(Optional.of(area));

            Mockito.when(cityRepository.save(
                    ArgumentMatchers.argThat(
                            savedCity -> {
                                boolean isSameName = savedCity.getCityName().equals(city.getCityName());
                                boolean isSameCode = savedCity.getSeverityStatus().equals(city.getSeverityStatus());
                                boolean isSameAreaId = savedCity.getArea().getId().equals(city.getArea().getId());
                                boolean isSameAreaName = savedCity.getArea().getAreaName().equals(city.getArea().getAreaName());
                                boolean isSameAreaCode = savedCity.getArea().getAreaCode().equals(city.getArea().getAreaCode());
                                return isSameCode && isSameName && isSameAreaId && isSameAreaName && isSameAreaCode;
                            }
                    )
            )).thenReturn(city);

            cityService.createCities(List.of(cityRequestDTO));
        }

        @Test
        @DisplayName("return 404-NOT_FOUND exception when no such area id")
        public void return404ExceptionWhenNoSuchAreaId() {

            Mockito
                    .when(areaRepository.findById(cityRequestDTO.getAreaId()))
                    .thenReturn(Optional.empty());

            ResponseStatusException ex = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> cityService.createCities(List.of(cityRequestDTO))
            );

            HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
            String expectedMessage = String.format("Area with id [%s] does not exist", area.getId());

            Assertions.assertEquals(expectedStatus, ex.getStatus());
            Assertions.assertEquals(expectedMessage, ex.getReason());
        }

        @Test
        @DisplayName("should return list od CityResponseDTO test")
        public void shouldReturnListOfCityResponseDTOTest() {
            Mockito
                    .when(areaRepository.findById(cityRequestDTO.getAreaId()))
                    .thenReturn(Optional.of(area));

            List<CityResponseDTO> response = cityService.createCities(List.of(cityRequestDTO));

            Assertions.assertEquals(response.get(0).getName(), city.getCityName());
            Assertions.assertEquals(response.get(0).getSeverityStatus(), city.getSeverityStatus());
            Assertions.assertEquals(response.get(0).getAreaId(), city.getArea().getId());
        }
    }


    @Nested
    public class getAllCitiesTest {

        @Test
        @DisplayName("Should return all cities DTO test")
        public void shouldReturnAllCitiesDTOTest() {

            City city2 = City.builder()
                    .cityName("nameTwo")
                    .severityStatus(SeverityStatus.GREEN)
                    .id(5L)
                    .area(area)
                    .build();

            Mockito
                    .when(cityRepository.findAll())
                    .thenReturn(List.of(city, city2));

            List<CityResponseDTO> response = cityService.getAllCities();

            Assertions.assertEquals(response.get(0).getCityId(), city.getId());
            Assertions.assertEquals(response.get(0).getName(), city.getCityName());
            Assertions.assertEquals(response.get(0).getSeverityStatus(), city.getSeverityStatus());
            Assertions.assertEquals(response.get(0).getAreaId(), city.getArea().getId());

            Assertions.assertEquals(response.get(1).getCityId(), city2.getId());
            Assertions.assertEquals(response.get(1).getName(), city2.getCityName());
            Assertions.assertEquals(response.get(1).getSeverityStatus(), city2.getSeverityStatus());
            Assertions.assertEquals(response.get(1).getAreaId(), city2.getArea().getId());
        }

        @Test
        @DisplayName("should return null")
        public void shouldReturnNullTest() {

            Mockito
                    .when(cityRepository.findAll())
                    .thenReturn(null);

            Assertions.assertEquals(null, cityService.getAllCities());
        }
    }


    @Nested
    public class getCityByNameTest {

        @Test
        @DisplayName("should return cityResponseDTO by name test")
        public void shouldReturnCityResponseDTOByNameTest() {

            Mockito.when(cityRepository.findByCityNameIgnoreCase(city.getCityName()))
                    .thenReturn(city);

            CityResponseDTO response = cityService.getCityByName(city.getCityName());

            Assertions.assertEquals(response.getAreaId(), city.getArea().getId());
            Assertions.assertEquals(response.getCityId(), city.getId());
            Assertions.assertEquals(response.getName(), city.getCityName());
            Assertions.assertEquals(response.getSeverityStatus(), city.getSeverityStatus());
        }

        @Test
        @DisplayName("should return 404-NOT_FOUND when no such city name test")
        public void shouldReturnNotFoundWhenSuchCityNameDoesNotExist() {
            Mockito
                    .when(cityRepository.findByCityNameIgnoreCase(city.getCityName()))
                    .thenReturn(null);

            HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
            String expectedMessage = String.format("City with name [%s] does not exist", city.getCityName());

            ResponseStatusException ex = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> cityService.getCityByName(city.getCityName())
            );

            Assertions.assertEquals(ex.getStatus(), expectedStatus);
            Assertions.assertEquals(ex.getReason(), expectedMessage);
        }
    }


    @Nested
    public class getCityByIdTest {

        @Test
        @DisplayName("should return cityResponseDTO by id test")
        public void shouldReturnCityResponseDTOByIdTest() {

            Mockito.when(cityRepository.findById(city.getId()))
                    .thenReturn(Optional.of(city));

            CityResponseDTO response = cityService.getCityById(city.getId());

            Assertions.assertEquals(city.getId(), response.getCityId());
            Assertions.assertEquals(city.getArea().getId(), response.getAreaId());
            Assertions.assertEquals(city.getCityName(), response.getName());
            Assertions.assertEquals(city.getSeverityStatus(), response.getSeverityStatus());
            Assertions.assertNotNull(response.getName());
            Assertions.assertNotNull(response.getSeverityStatus());
            Assertions.assertNotNull(response.getCityId());
            Assertions.assertNotNull(response.getAreaId());
        }

        @Test
        @DisplayName("should throw 404-NOT_FOUND when no such CityId")
        public void shouldThrow404WhenNoSuchCityIdTest() {

            Mockito
                    .when(cityRepository.findById(city.getId()))
                    .thenReturn(Optional.empty());

            HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
            String expectedMessage = String.format("City with id [%s] does not exist", city.getId());

            ResponseStatusException ex = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> cityService.getCityById(city.getId())
            );

            Assertions.assertEquals(expectedStatus, ex.getStatus());
            Assertions.assertEquals(expectedMessage, ex.getReason());
        }
    }
}
