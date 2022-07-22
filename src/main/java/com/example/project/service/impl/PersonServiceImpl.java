package com.example.project.service.impl;

import com.example.project.dto.*;
import com.example.project.entity.City;
import com.example.project.entity.Person;
import com.example.project.repository.CityRepository;
import com.example.project.repository.PersonRepository;
import com.example.project.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<PersonResponseDTO> createPersons(List<PersonRequestDTO> request) {

        List<Person> persons = request.stream()
                .map(x -> {
                    City city = cityRepository
                            .findById(x.getCityId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    String.format("City with id [%s] does not exist", x.getCityId()))
                            );

                    Person person = Person.builder()
                            .firstName(x.getFirstName())
                            .lastName(x.getLastName())
                            .dateOfBirth(x.getDateOfBirth())
                            .email(x.getEmail())
                            .phone(x.getPhone())
                            .city(city)
                            .build();
                    personRepository.save(person);
                    return person;
                }).collect(Collectors.toList());

        return persons.stream()
                .map(this::personToDTO).collect(Collectors.toList());
    }

    @Override
    public void updatePerson(Long id, PersonUpdateRequestDTO request) {
        Person person = personRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Person with id [%s] does not exist", id)));

        person.setFirstName(request.getFirstName());
        person.setLastName(request.getLastName());
        person.setDateOfBirth(request.getDateOfBirth());
        person.setEmail(request.getEmail());
        person.setPhone(request.getPhone());

        personRepository.save(person);
    }

    @Override
    public PersonResponseDTO postGuardian(Long personId, Long guardianId) {
        Person person = personRepository
                .findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Person with id [%s] does not exist", personId)));

        Person guardian = personRepository
                .findById(guardianId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Guardian with id [%s] does not exist", guardianId)));

        if (guardian.getGuardianId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Person with id [%s] can not be a guardian", guardianId));
        }

        person.setGuardianId(guardianId);
        personRepository.save(person);
        return personToDTO(person);
    }

    @Override
    public PersonResponseDTO changeGuardian(GuardianChangeRequestDTO request) {

        Person guardianFrom = personRepository
                .findById(request.getFromGuardian())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Guardian with id [%s] does not exist", request.getFromGuardian()))
                );

        Person guardianTo = personRepository
                .findById(request.getToGuardian())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Guardian with id [%s] does not exist", request.getFromGuardian()))
                );

        if (guardianTo.getGuardianId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Person with id [%s] can not be a guardian", request.getToGuardian()));
        }

        List<Long> childrenId = request.getChildrenId();
        List<Long> fromGuardianAllChild = personRepository.findAllByGuardianId(request.getFromGuardian())
                .stream()
                .map(Person::getId)
                .collect(Collectors.toList());

        for (Long x : childrenId) {
            if (!fromGuardianAllChild.contains(x)) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        String.format("Person with id [%s] is not a guardian for person with id [%s]",
                                request.getFromGuardian(), x));
            }
        }

        childrenId.stream().forEach(y -> {
            Person child = personRepository
                    .findById(y)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Person with id [%s] does not exist", y))
                    );
            child.setGuardianId(request.getToGuardian());
            personRepository.save(child);
        });
        return personToDTO(guardianFrom);
    }

    @Override
    public PersonResponseDTO getPersonById(Long id) {
        Person person = personRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Person with id [%s] does not exist", id))
                );
        return personToDTO(person);
    }

    @Override
    public PersonResponseDTO getPersonByEmail(String email) {
        try {
            return personToDTO(personRepository.findByEmail(email));
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Person with email [%s] does not exist", email));
        }
    }

    @Override
    public PersonResponseDTO movePerson(PersonMoveRequestDTO request) {
        Person person = personRepository
                .findById(request.getPersonId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Person with id [%s] does not exist", request.getPersonId()))
                );

        City fromCity = cityRepository
                .findById(request.getFromCityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("City with id [%s] does not exist", request.getFromCityId()))
                );

        City toCity = cityRepository
                .findById(request.getToCityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("City with id [%s] does not exist", request.getToCityId()))
                );

        if (person.getGuardianId() != null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("Person with id [%s] can not move without guardian", request.getPersonId())
            );
        }

        if (!person.getCity().getId().equals(request.getFromCityId())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("Person with id [%s] do not live in the city with id [%s]", request.getPersonId(), request.getFromCityId())
            );
        }

        var child = personRepository.findAllByGuardianId(request.getPersonId());
        for (Person p : child) {
            p.setCity(toCity);
        }
        person.setCity(toCity);

        return personToDTO(person);
    }

    private PersonResponseDTO personToDTO(Person person) {

        List<PersonChildrenResponseDTO> childrenDTO = personRepository.findAllByGuardianId(person.getGuardianId()).stream()
                .map(x -> {
                            PersonChildrenResponseDTO children = PersonChildrenResponseDTO.builder()
                                    .firstName(x.getFirstName())
                                    .lastName(x.getLastName())
                                    .dateOfBirth(x.getDateOfBirth())
                                    .email(x.getEmail())
                                    .phone(x.getPhone())
                                    .cityId(x.getCity().getId())
                                    .areaId(x.getCity().getArea().getId())
                                    .guardianId(x.getGuardianId())
                                    .build();
                            return children;
                        }
                ).collect(Collectors.toList());

        return PersonResponseDTO.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .dateOfBirth(person.getDateOfBirth())
                .email(person.getEmail())
                .phone(person.getPhone())
                .cityId(person.getCity().getId())
                .areaId(person.getCity().getArea().getId())
                .guardianId(person.getGuardianId())
                .children(childrenDTO)
                .build();
    }
}
