package com.example.project.entity.notification;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class NotificationConverterTest {

    @Test
    @DisplayName("should return string")
    public void shouldReturnString() {
        NotificationConverter converter = new NotificationConverter();
        var givenA = "one";
        var givenB = "two";
        var givenList = List.of(givenA, givenB);
        var expected = "one,two";
        var result = converter.convertToDatabaseColumn(givenList);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("should return List<String>")
    public void shouldReturnList(){
        NotificationConverter converter = new NotificationConverter();
        var given = "one,two";
        var expected = List.of("one", "two");
        var result = converter.convertToEntityAttribute(given);

        Assertions.assertEquals(expected, result);
    }
}
