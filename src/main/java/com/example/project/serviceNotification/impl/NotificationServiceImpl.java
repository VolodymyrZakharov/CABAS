package com.example.project.serviceNotification.impl;

import com.example.project.entity.Area;
import com.example.project.entity.City;
import com.example.project.entity.Person;
import com.example.project.entity.notification.Notification;
import com.example.project.entity.severity.SeverityStatus;
import com.example.project.repository.AreaRepository;
import com.example.project.repository.CityRepository;
import com.example.project.repository.NotificationRepository;
import com.example.project.repository.PersonRepository;
import com.example.project.serviceNotification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public void notify(String areaCode, SeverityStatus severityStatus) {

        Area area = areaRepository.findByAreaCodeIgnoreCase(areaCode);
        if (area == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Area code [%s] not found", areaCode));
        }

        List<City> cities = cityRepository.findAllByArea(area);
        if (cities.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No any city registered in area [%s]", areaCode));
        }

        cities.stream().forEach(x -> {
            x.setSeverityStatus(severityStatus);
            cityRepository.save(x);
        });

        List<Person> persons = personRepository.findAllByCityIn(cities);
        if (persons.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No any people registered in area"));
        }

        Notification notification = Notification.builder()
                .areaId(area.getId())
                .severityStatus(severityStatus)
                .people(persons.stream().map(Person::getId).collect(Collectors.toList()))
                .build();
        notificationRepository.save(notification);

        persons.stream().forEach(x -> {
            System.out.println(x.getFirstName());
            System.out.println(x.getLastName());
            System.out.println(x.getPhone());
            System.out.println(x.getEmail());
        });
    }
}
