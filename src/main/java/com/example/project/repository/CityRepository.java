package com.example.project.repository;

import com.example.project.entity.Area;
import com.example.project.entity.City;
import com.example.project.entity.severity.SeverityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    City findByCityNameIgnoreCase(String name);

    List<City> findAllByArea(Area area);

}
