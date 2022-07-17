package com.example.project.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Column(name = "date_of_birth", nullable = false)
    LocalDate dateOfBirth;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "phone", unique = true)
    String phone;

    @JoinColumn
    @ManyToOne
    City city;

    @Column(name = "guardian_id")
    Long guardianId;


}
