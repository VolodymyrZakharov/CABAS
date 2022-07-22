package com.example.project.service.impl;

import com.example.project.dto.AreaRequestDTO;
import com.example.project.dto.AreaResponseDTO;
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
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AreaServiceImplTest {

    @Mock
    private AreaRepository areaRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private AreaServiceImpl areaService;

    AreaRequestDTO areaRequestDTO = AreaRequestDTO.builder()
            .areaName("areaOne")
            .areaCode("AO")
            .build();

    Area area = Area.builder()
            .id(1L)
            .areaName(areaRequestDTO.getAreaName())
            .areaCode(areaRequestDTO.getAreaCode())
            .build();

    City city = City.builder()
            .id(2L)
            .area(area)
            .cityName("cityName")
            .severityStatus(SeverityStatus.GREEN)
            .build();

    @Nested
    public class createAreaTest {

        @Test
        @DisplayName("should save area in repository")
        public void shouldSaveAreaTest() {
            Mockito.when(areaRepository.save(
                    ArgumentMatchers.argThat(
                            savedArea -> {
                                boolean isSameName = savedArea.getAreaName().equals(area.getAreaName());
                                boolean isSameCode = savedArea.getAreaCode().equals(area.getAreaCode());
                                return isSameCode && isSameName;
                            }
                    )
            )).thenReturn(area);

            areaService.createArea(List.of(areaRequestDTO));
        }

        @Test
        @DisplayName("should create area and return areaResponseDTO")
        public void shouldCreateAreaAndReturnAreaResponseDTOTest() {
            Mockito
                    .when(areaRepository.save(ArgumentMatchers.argThat(
                            savedArea -> {
                                boolean isSameName = savedArea.getAreaName().equals(area.getAreaName());
                                boolean isSameCode = savedArea.getAreaCode().equals(area.getAreaCode());
                                return isSameCode && isSameName;
                            }
                    )))
                    .thenReturn(area);

            Mockito
                    .when(cityRepository.findAllByArea(ArgumentMatchers.argThat(
                            savedArea -> {
                                boolean isSameName = savedArea.getAreaName().equals(area.getAreaName());
                                boolean isSameCode = savedArea.getAreaCode().equals(area.getAreaCode());
                                return isSameCode && isSameName;
                            }
                    )))
                    .thenReturn(List.of());

            List<AreaResponseDTO> response = areaService.createArea(List.of(areaRequestDTO));
            areaService.createArea(List.of(areaRequestDTO));
            Assertions.assertEquals(response.get(0).getAreaName(), area.getAreaName());
            Assertions.assertEquals(response.get(0).getAreaCode(), area.getAreaCode());
            Assertions.assertTrue(response.get(0).getCityIds().isEmpty());
        }
    }


    @Nested
    public class getAreaByIdTest {

        @Test
        @DisplayName("should return areaResponseDTO with cities when call get area by id")
        public void shouldReturnAreaResponseDTOWithCitiesTest() {
            Mockito
                    .when(areaRepository.findById(area.getId()))
                    .thenReturn(Optional.of(area));

            Mockito
                    .when(cityRepository.findAllByArea(area))
                    .thenReturn(List.of(city));

            AreaResponseDTO response = areaService.getAreaById(area.getId());

            Assertions.assertEquals(area.getId(), response.getId());
            Assertions.assertEquals(area.getAreaName(), response.getAreaName());
            Assertions.assertEquals(area.getAreaCode(), response.getAreaCode());
            Assertions.assertNotNull(response.getCityIds());
            Assertions.assertNotNull(response.getId());
            Assertions.assertNotNull(response.getAreaCode());
            Assertions.assertNotNull(response.getAreaName());
        }

        @Test
        @DisplayName("should return areaResponseDTO when area has not cities")
        public void shouldReturnAreaResponseDTOWithoutCitiesTest() {
            Mockito
                    .when(areaRepository.findById(area.getId()))
                    .thenReturn(Optional.of(area));

            Mockito
                    .when(cityRepository.findAllByArea(area))
                    .thenReturn(List.of());

            AreaResponseDTO response = areaService.getAreaById(area.getId());

            Assertions.assertEquals(area.getId(), response.getId());
            Assertions.assertEquals(area.getAreaName(), response.getAreaName());
            Assertions.assertEquals(area.getAreaCode(), response.getAreaCode());
            Assertions.assertTrue(response.getCityIds().isEmpty());
            Assertions.assertNotNull(response.getId());
            Assertions.assertNotNull(response.getAreaCode());
            Assertions.assertNotNull(response.getAreaName());
        }

        @Test
        @DisplayName("should throw 404-NOT_FOUND when no such Id")
        public void shouldThrow404WhenNoSuchIdTest() {
            Mockito
                    .when(areaRepository.findById(area.getId()))
                    .thenReturn(Optional.empty());

            HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
            String expectedMessage = String.format("Area with id [%s] not found", area.getId());

            ResponseStatusException ex = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> areaService.getAreaById(area.getId())
            );

            Assertions.assertEquals(expectedStatus, ex.getStatus());
            Assertions.assertEquals(expectedMessage, ex.getReason());
        }
    }


    @Nested
    public class getAreaByNameTest {

        @Test
        @DisplayName("should return areaResponseDTO with cities")
        public void shouldReturnAreaResponseDTOWithCitiesTest() {
            Mockito
                    .when(areaRepository.findByAreaNameIgnoreCase(area.getAreaName()))
                    .thenReturn(area);

            Mockito
                    .when(cityRepository.findAllByArea(area))
                    .thenReturn(List.of(city));

            AreaResponseDTO areaResponseDTO = areaService.getAreaByName(area.getAreaName());
            Assertions.assertEquals(areaResponseDTO.getAreaName(), area.getAreaName());
            Assertions.assertEquals(areaResponseDTO.getAreaCode(), area.getAreaCode());
            Assertions.assertEquals(areaResponseDTO.getCityIds(), List.of(city.getId()));
            Assertions.assertEquals(areaResponseDTO.getId(), area.getId());
            Assertions.assertNotNull(areaResponseDTO.getId());
            Assertions.assertNotNull(areaResponseDTO.getAreaCode());
            Assertions.assertNotNull(areaResponseDTO.getAreaName());
        }

        @Test
        @DisplayName("should return areaResponseDTO without cities")
        public void shouldReturnAreaResponseDTOWithoutCitiesTest() {
            Mockito
                    .when(areaRepository.findByAreaNameIgnoreCase(area.getAreaName()))
                    .thenReturn(area);

            Mockito
                    .when(cityRepository.findAllByArea(area))
                    .thenReturn(List.of());

            AreaResponseDTO areaResponseDTO = areaService.getAreaByName(area.getAreaName());
            Assertions.assertEquals(areaResponseDTO.getAreaName(), area.getAreaName());
            Assertions.assertEquals(areaResponseDTO.getAreaCode(), area.getAreaCode());
            Assertions.assertEquals(areaResponseDTO.getCityIds(), List.of());
            Assertions.assertEquals(areaResponseDTO.getId(), area.getId());
            Assertions.assertNotNull(areaResponseDTO.getId());
            Assertions.assertNotNull(areaResponseDTO.getAreaCode());
            Assertions.assertNotNull(areaResponseDTO.getAreaName());
        }

        @Test
        @DisplayName("should throw 404-NOT_FOUND when no such name")
        public void shouldThrow404WhenNoSuchNameTest() {
            Mockito
                    .when(areaRepository.findByAreaNameIgnoreCase(area.getAreaName()))
                    .thenReturn(null);

            HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
            String expectedMessage = String.format("Area with name [%s] does not exist", area.getAreaName());

            ResponseStatusException ex = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> areaService.getAreaByName(area.getAreaName())
            );

            Assertions.assertEquals(ex.getStatus(), expectedStatus);
            Assertions.assertEquals(ex.getReason(), expectedMessage);

            areaService.getAreaByName(area.getAreaName());
        }
    }
}
