package org.lamisplus.base.application.util;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converter to persist LocalDate and LocalDateTime with
 * JPA 2.1 and Hibernate older than 5.0 version
 **/

@Converter(autoApply = true)
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, Time>{

    @Override
    public Time convertToDatabaseColumn(LocalTime localTime) {
        return (localTime == null ? null : Time.valueOf(localTime));
    }

    @Override
    public LocalTime convertToEntityAttribute(Time time) {
        // Converting 'dd-MM-yyyy' SQL date to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("K:mm a");
        //LocalTime localTime = LocalTime.parse(formatter.format(time.toLocalTime()), formatter);

        return (time == null ? null : LocalTime.parse(formatter.format(time.toLocalTime()), formatter));
    }

}