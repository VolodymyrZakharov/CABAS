package com.example.project.serviseNotification.impl;

import com.example.project.entity.City;
import com.example.project.entity.Person;
import com.example.project.entity.severity.SeverityStatus;
import com.example.project.repository.CityRepository;
import com.example.project.repository.PersonRepository;
import com.example.project.serviseNotification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void notify(Long areaCode, SeverityStatus severityStatus) {
        List<City> cities = cityRepository.findAllBySeverityStatus(severityStatus);
        List<Person> persons = personRepository.findAllByCityIn(cities);
        persons.stream().forEach(x -> {
            System.out.println(x.getFirstName());
            System.out.println(x.getLastName());
            System.out.println(x.getPhone());
            System.out.println(x.getEmail());
        });
    }
}
