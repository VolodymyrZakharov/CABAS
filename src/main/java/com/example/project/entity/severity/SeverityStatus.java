package com.example.project.entity.severity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;

@Table(name = "severity_status")
@AllArgsConstructor
@Getter
public enum SeverityStatus {
    GREEN(1, "green"),
    YELLOW(2, "yellow"),
    ORANGE(3, "orange"),
    RED(4, "red");

    private final Integer integerValue;
    private final String stringValue;

    public static SeverityStatus findByIntegerValue(Integer severityId) {
        if (severityId == null) {
            return null;
        }

        return Arrays.stream(SeverityStatus.values())
                .filter(x -> x.getIntegerValue().equals(severityId))
                .findFirst()
                .orElse(null);
    }

    @JsonCreator
    public static SeverityStatus findByStringValue(String statusId) {
        if (statusId == null) {
            return null;
        }

        return Arrays.stream(SeverityStatus.values())
                .filter(x -> x.getStringValue().equals(statusId))
                .findFirst()
                .orElse(null);
    }
}
