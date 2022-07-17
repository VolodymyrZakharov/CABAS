package com.example.project.entity.severity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class SeverityStatusConverter implements AttributeConverter<SeverityStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SeverityStatus status) {
        return status == null ? null : status.getIntegerValue();
    }

    @Override
    public SeverityStatus convertToEntityAttribute(Integer integer) {
        return integer == null ? null : SeverityStatus.findByIntegerValue(integer);
    }
}