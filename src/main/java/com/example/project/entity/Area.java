package com.example.project.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "area")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "area_name", unique = true, nullable = false)
    String areaName;

    @Column(name = "area_code", unique = true, nullable = false)
    String areaCode;
}
