package com.example.project.entity;

import com.example.project.entity.severity.SeverityStatus;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private SeverityStatus severityStatus;

    @JoinColumn
    @ManyToOne
    private Area area;
}
