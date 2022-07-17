package com.example.project.repository;

import com.example.project.entity.City;
import com.example.project.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findAllByGuardianId(Long id);

    Person findByEmail(String email);

    List<Person> findAllByCityIn(List<City> cities);
}
