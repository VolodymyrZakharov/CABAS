package com.example.project.entity.severity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SeverityStatusConverterTest {

    @Test
    @DisplayName("should return entity attribute green")
    public void shouldReturnEntityAttributeGreen() {
        SeverityStatusConverter converter = new SeverityStatusConverter();
        Integer given = 1;
        var expected = SeverityStatus.GREEN;

        var result = converter.convertToEntityAttribute(given);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("should return entity attribute yellow")
    public void shouldReturnEntityAttributeYellow() {
        SeverityStatusConverter converter = new SeverityStatusConverter();
        Integer given = 2;
        var expected = SeverityStatus.YELLOW;
        var result = converter.convertToEntityAttribute(given);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("should return entity attribute orange")
    public void shouldReturnEntityAttributeOrange() {
        SeverityStatusConverter converter = new SeverityStatusConverter();
        Integer given = 3;
        var expected = SeverityStatus.ORANGE;
        var result = converter.convertToEntityAttribute(given);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("should return entity attribute red")
    public void shouldReturnEntityAttributeRed() {
        SeverityStatusConverter converter = new SeverityStatusConverter();
        Integer given = 4;
        var expected = SeverityStatus.RED;
        var result = converter.convertToEntityAttribute(given);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("should return one")
    public void shouldReturnOne(){
        SeverityStatusConverter converter = new SeverityStatusConverter();
        var given = SeverityStatus.GREEN;
        Integer expected = 1;
        var result = converter.convertToDatabaseColumn(given);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("should return two")
    public void shouldReturnTwo(){
        SeverityStatusConverter converter = new SeverityStatusConverter();
        var given = SeverityStatus.YELLOW;
        Integer expected = 2;
        var result = converter.convertToDatabaseColumn(given);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("should return three")
    public void shouldReturnThree(){
        SeverityStatusConverter converter = new SeverityStatusConverter();
        var given = SeverityStatus.ORANGE;
        Integer expected = 3;
        var result = converter.convertToDatabaseColumn(given);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("should return four")
    public void shouldReturnFour(){
        SeverityStatusConverter converter = new SeverityStatusConverter();
        var given = SeverityStatus.RED;
        Integer expected = 4;
        var result = converter.convertToDatabaseColumn(given);

        Assertions.assertEquals(expected, result);
    }
}
