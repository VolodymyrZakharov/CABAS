package com.example.project.repository;

import com.example.project.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long> {

    Area findByAreaNameIgnoreCase(String areaName);

    Area findByAreaCodeIgnoreCase(String areaCode);
}
