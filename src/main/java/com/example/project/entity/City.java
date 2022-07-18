package com.example.project.entity;

import com.example.project.entity.severity.SeverityStatus;
import com.example.project.entity.severity.SeverityStatusConverter;
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
    private Long id;

    @Column(name = "city_name", unique = true, nullable = false)
    private String cityName;

    @Column(name = "status", nullable = false)
    @Convert(converter = SeverityStatusConverter.class)
    private SeverityStatus severityStatus;

    @JoinColumn
    @ManyToOne
    private Area area;
}
