package com.example.project.entity;

import com.example.project.entity.severity.SeverityStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "city_name", unique = true, nullable = false)
    String cityName;

    @Column(name = "status", nullable = false)
    SeverityStatus severityStatus;

    @JoinColumn
    @ManyToOne
    Area area;
}
