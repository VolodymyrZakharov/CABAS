package com.example.project.entity.notification;

import com.example.project.entity.severity.SeverityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "area_id", nullable = false)
    private Long areaId;

    @Column(name = "severity_status", nullable = false)
    private SeverityStatus severityStatus;

    @Convert(converter = NotificationConverter.class)
    @Column(name = "people_notified")
    private List<Long> people;

    @CreatedDate
    @Column(name = "created_on")
    private LocalDateTime createdOn;
}
