package com.example.project.service.impl;

import com.example.project.dto.PersonChildrenResponseDTO;
import com.example.project.dto.PersonRequestDTO;
import com.example.project.dto.PersonResponseDTO;
import com.example.project.dto.PersonUpdateRequestDTO;
import com.example.project.entity.Area;
import com.example.project.entity.City;
import com.example.project.entity.Person;
import com.example.project.entity.severity.SeverityStatus;
import com.example.project.repository.CityRepository;
import com.example.project.repository.PersonRepository;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    @Mock
    CityRepository cityRepository;

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonServiceImpl personService;

    Area area = Area.builder()
            .id(1L)
            .areaName("areaName")
            .areaCode("AC")
            .build();

    City city = City.builder()
            .id(2L)
            .cityName("cityName")
            .severityStatus(SeverityStatus.GREEN)
            .area(area)
            .build();

    City city2 = City.builder()
            .id(8L)
            .cityName("city2Name")
            .severityStatus(SeverityStatus.RED)
            .area(area)
            .build();

    PersonRequestDTO request = PersonRequestDTO.builder()
            .firstName("firstName")
            .lastName("lastName")
            .dateOfBirth(LocalDate.of(2000, 12, 12))
            .email("email@gmail.com")
            .phone("+63 917 123 4567")
            .cityId(city.getId())
            .build();

    PersonUpdateRequestDTO personUpdateRequestDTO = PersonUpdateRequestDTO.builder()
            .firstName("updatedFirstName")
            .lastName("updatedLastName")
            .dateOfBirth(LocalDate.of(1993, 12, 13))
            .email("updatedEmail@gmail.com")
            .phone("+63 917 123 4569")
            .build();

    Person guardian = Person.builder()
            .id(4L)
            .firstName("guardianFirstName")
            .lastName("guardianLastName")
            .dateOfBirth(LocalDate.of(1995, 11, 12))
            .email("guardianEmail@gmail.com")
            .city(city)
            .phone("+63 917 123 4563")
            .guardianId(null)
            .build();

    Person person = Person.builder()
            .id(3L)
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .dateOfBirth(request.getDateOfBirth())
            .email(request.getEmail())
            .city(city)
            .phone(request.getPhone())
            .guardianId(null)
            .build();


    @Nested
    public class createPersonsTest {

        @Test
        @DisplayName("should throw 404-NOT_FOUND when no such CityId")
        public void shouldThrow404WhenNoSuchCityIdTest() {

            Mockito
                    .when(cityRepository.findById(request.getCityId()))
                    .thenReturn(Optional.empty());

            HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
            String expectedMessage = String.format("City with id [%s] does not exist", request.getCityId());

            ResponseStatusException ex = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> personService.createPersons(List.of(request))
            );

            Assertions.assertEquals(expectedStatus, ex.getStatus());
            Assertions.assertEquals(expectedMessage, ex.getReason());
        }

        @Test
        @DisplayName("should save person")
        public void shouldSavePersonTest() {

            Mockito
                    .when(cityRepository.findById(request.getCityId()))
                    .thenReturn(Optional.of(city));


            Mockito.when(personRepository.save(
                    ArgumentMatchers.argThat(
                            savedPerson -> {
                                boolean isSameFirstName = savedPerson.getFirstName().equals(person.getFirstName());
                                boolean isSameLastName = savedPerson.getLastName().equals(person.getLastName());
                                boolean isSameDateOfBirth = savedPerson.getDateOfBirth().equals(person.getDateOfBirth());
                                boolean isSameEmail = savedPerson.getEmail().equals(person.getEmail());
                                boolean isSamePhone = savedPerson.getPhone().equals(person.getPhone());
                                boolean isSameCity = savedPerson.getCity().getId().equals(person.getCity().getId());
                                return isSameFirstName && isSameLastName && isSameDateOfBirth && isSameEmail && isSamePhone & isSameCity;
                            }
                    )
            )).thenReturn(person);

            personService.createPersons(List.of(request));
        }

        @Test
        @DisplayName("should return list of personResponseDTO")
        public void shouldReturnListOfPersonResponseDTOTest() {

            Mockito
                    .when(cityRepository.findById(request.getCityId()))
                    .thenReturn(Optional.of(city));

            List<PersonResponseDTO> response = personService.createPersons(List.of(request));

            Assertions.assertEquals(response.get(0).getFirstName(), person.getFirstName());
            Assertions.assertEquals(response.get(0).getLastName(), person.getLastName());
            Assertions.assertEquals(response.get(0).getEmail(), person.getEmail());
            Assertions.assertEquals(response.get(0).getPhone(), person.getPhone());
            Assertions.assertEquals(response.get(0).getCityId(), person.getCity().getId());
            Assertions.assertEquals(response.get(0).getAreaId(), person.getCity().getArea().getId());
            Assertions.assertNull(response.get(0).getGuardianId());
        }
    }


    @Nested
    public class updatePersonTest {

        @Test
        @DisplayName("should throw 404-NOT_FOUND when no such PersonId")
        public void shouldThrow404WhenNoSuchPersonIdTest() {

            Mockito
                    .when(personRepository.findById(person.getId()))
                    .thenReturn(Optional.empty());

            HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
            String expectedMessage = String.format("Person with id [%s] does not exist", person.getId());

            ResponseStatusException ex = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> personService.updatePerson(person.getId(), personUpdateRequestDTO)
            );

            Assertions.assertEquals(expectedStatus, ex.getStatus());
            Assertions.assertEquals(expectedMessage, ex.getReason());
        }

        @Test
        @DisplayName("should Update Person Test")
        public void shouldUpdatePersonTest() {

            Mockito
                    .when(personRepository.findById(person.getId()))
                    .thenReturn(Optional.of(person));

            Mockito.when(personRepository.save(
                    ArgumentMatchers.argThat(
                            savedPerson -> {
                                boolean isSameFirstName = savedPerson.getFirstName().equals(personUpdateRequestDTO.getFirstName());
                                boolean isSameLastName = savedPerson.getLastName().equals(personUpdateRequestDTO.getLastName());
                                boolean isSameDateOfBirth = savedPerson.getDateOfBirth().equals(personUpdateRequestDTO.getDateOfBirth());
                                boolean isSameEmail = savedPerson.getEmail().equals(personUpdateRequestDTO.getEmail());
                                boolean isSamePhone = savedPerson.getPhone().equals(personUpdateRequestDTO.getPhone());
                                return isSameFirstName && isSameLastName && isSameDateOfBirth && isSameEmail && isSamePhone;
                            }
                    )
            )).thenReturn(person);

            personService.updatePerson(person.getId(), personUpdateRequestDTO);
        }

        @Nested
        public class postGuardianTest {

            @Test
            @DisplayName("should return 404-NOT_FOUND when person id does not exist")
            public void shouldReturnNotFoundPersonTest() {

                Mockito
                        .when(personRepository.findById(person.getId()))
                        .thenReturn(Optional.empty());

                HttpStatus status = HttpStatus.NOT_FOUND;
                String message = String.format("Person with id [%s] does not exist", person.getId());

                ResponseStatusException ex = Assertions.assertThrows(
                        ResponseStatusException.class,
                        () -> personService.postGuardian(person.getId(), guardian.getId())
                );

                Assertions.assertEquals(status, ex.getStatus());
                Assertions.assertEquals(message, ex.getReason());
            }

            @Test
            @DisplayName("should return 404-NOT_FOUND when guardian id does not exist")
            public void shouldReturnNotFoundGuardianTest() {

                Mockito
                        .when(personRepository.findById(person.getId()))
                        .thenReturn(Optional.of(person));

                Mockito
                        .when(personRepository.findById(guardian.getId()))
                        .thenReturn(Optional.empty());

                HttpStatus status = HttpStatus.NOT_FOUND;
                String message = String.format("Guardian with id [%s] does not exist", guardian.getId());

                ResponseStatusException ex = Assertions.assertThrows(
                        ResponseStatusException.class,
                        () -> personService.postGuardian(person.getId(), guardian.getId())
                );

                Assertions.assertEquals(status, ex.getStatus());
                Assertions.assertEquals(message, ex.getReason());
            }

            @Test
            @DisplayName("should return 409-CONFLICT when person can not to be a guardian")
            public void shouldReturnConflictWhenPersonCanNotBeGuardianTest() {

                Person wrongGuardian = Person.builder()
                        .id(4L)
                        .firstName("guardianFirstName")
                        .lastName("guardianLastName")
                        .dateOfBirth(LocalDate.of(1995, 11, 12))
                        .email("guardianEmail@gmail.com")
                        .city(city)
                        .phone("+63 917 123 4563")
                        .guardianId(3L)
                        .build();

                Mockito
                        .when(personRepository.findById(person.getId()))
                        .thenReturn(Optional.of(person));

                Mockito
                        .when(personRepository.findById(wrongGuardian.getId()))
                        .thenReturn(Optional.of(wrongGuardian));

                HttpStatus status = HttpStatus.CONFLICT;
                String message = String.format("Person with id [%s] can not be a guardian", wrongGuardian.getId());

                ResponseStatusException ex = Assertions.assertThrows(
                        ResponseStatusException.class,
                        () -> personService.postGuardian(person.getId(), wrongGuardian.getId())
                );

                Assertions.assertEquals(status, ex.getStatus());
                Assertions.assertEquals(message, ex.getReason());
            }

            @Test
            @DisplayName("should return personResponseDTO when post guardian test")
            public void shouldReturnPersonResponseDTOWhenPostGuardianTest() {

                Mockito
                        .when(personRepository.findById(person.getId()))
                        .thenReturn(Optional.of(person));

                Mockito
                        .when(personRepository.findById(guardian.getId()))
                        .thenReturn(Optional.of(guardian));

                Mockito
                        .when(personRepository.save(person))
                        .thenReturn(person);

                PersonResponseDTO expectedPersonResponseDTO = PersonResponseDTO.builder()
                        .id(person.getId())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .dateOfBirth(person.getDateOfBirth())
                        .email(person.getEmail())
                        .phone(person.getPhone())
                        .cityId(person.getCity().getId())
                        .areaId(person.getCity().getArea().getId())
                        .guardianId(person.getGuardianId())
                        .children(null)
                        .build();

                PersonResponseDTO personResponseDTO = personService.postGuardian(person.getId(), guardian.getId());

                Assertions.assertEquals(expectedPersonResponseDTO.getId(), personResponseDTO.getId());
                Assertions.assertEquals(expectedPersonResponseDTO.getFirstName(), personResponseDTO.getFirstName());
                Assertions.assertEquals(expectedPersonResponseDTO.getLastName(), personResponseDTO.getLastName());
                Assertions.assertEquals(expectedPersonResponseDTO.getDateOfBirth(), personResponseDTO.getDateOfBirth());
                Assertions.assertEquals(expectedPersonResponseDTO.getEmail(), personResponseDTO.getEmail());
                Assertions.assertEquals(expectedPersonResponseDTO.getPhone(), personResponseDTO.getPhone());
                Assertions.assertEquals(expectedPersonResponseDTO.getCityId(), personResponseDTO.getCityId());
                Assertions.assertEquals(expectedPersonResponseDTO.getAreaId(), personResponseDTO.getAreaId());
                Assertions.assertNull(expectedPersonResponseDTO.getGuardianId());
            }

        }
    }
}
//
//    PersonChildrenResponseDTO expectedChildrenResponseDTO = PersonChildrenResponseDTO.builder()
//            .firstName(person.getFirstName())
//            .lastName(person.getLastName())
//            .dateOfBirth(person.getDateOfBirth())
//            .email(person.getEmail())
//            .phone(person.getPhone())
//            .cityId(person.getCity().getId())
//            .areaId(person.getCity().getArea().getId())
//            .guardianId(guardian.getId())
//            .build();
//
//    PersonResponseDTO expectedPersonResponseDTO = PersonResponseDTO.builder()
//            .id(guardian.getId())
//            .firstName(guardian.getFirstName())
//            .lastName(guardian.getLastName())
//            .dateOfBirth(guardian.getDateOfBirth())
//            .email(guardian.getEmail())
//            .phone(guardian.getPhone())
//            .cityId(guardian.getCity().getId())
//            .areaId(guardian.getCity().getArea().getId())
//            .guardianId(guardian.getGuardianId())
//            .children(List.of(expectedChildrenResponseDTO))
//            .build();
//
//    PersonResponseDTO personResponseDTO = personService.postGuardian(person.getId(), guardian.getId());
//
//                Assertions.assertEquals(expectedPersonResponseDTO.getId(), personResponseDTO.getId());
//                        Assertions.assertEquals(expectedPersonResponseDTO.getFirstName(), guardian.getFirstName());
//                        Assertions.assertEquals(expectedPersonResponseDTO.getLastName(), guardian.getLastName());
//                        Assertions.assertEquals(expectedPersonResponseDTO.getDateOfBirth(), guardian.getDateOfBirth());
//                        Assertions.assertEquals(expectedPersonResponseDTO.getEmail(), guardian.getEmail());
//                        Assertions.assertEquals(expectedPersonResponseDTO.getPhone(), guardian.getPhone());
//                        Assertions.assertEquals(expectedPersonResponseDTO.getCityId(), guardian.getCity().getId());
//                        Assertions.assertEquals(expectedPersonResponseDTO.getAreaId(), guardian.getCity().getArea().getId());
//                        Assertions.assertEquals(expectedPersonResponseDTO.getGuardianId(), guardian.getGuardianId());
//                        Assertions.assertEquals(expectedChildrenResponseDTO.getFirstName(), person.getFirstName());
//                        Assertions.assertEquals(expectedChildrenResponseDTO.getLastName(), person.getLastName());
//                        Assertions.assertEquals(expectedChildrenResponseDTO.getDateOfBirth(), person.getDateOfBirth());
//                        Assertions.assertEquals(expectedChildrenResponseDTO.getEmail(), person.getEmail());
//                        Assertions.assertEquals(expectedChildrenResponseDTO.getPhone(), person.getPhone());
//                        Assertions.assertEquals(expectedChildrenResponseDTO.getCityId(), person.getCity().getId());
//                        Assertions.assertEquals(expectedChildrenResponseDTO.getAreaId(), person.getCity().getArea().getId());
//                        Assertions.assertEquals(expectedChildrenResponseDTO.getGuardianId(), person.getGuardianId());
