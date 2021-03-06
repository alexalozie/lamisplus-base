package org.lamisplus.modules.base.util.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate entityValue) {
        if (entityValue == null) {
            return null;
        }
        return Date.valueOf(entityValue);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        // Converting 'dd-MM-yyyy' SQL date to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(formatter.format(databaseValue.toLocalDate()), formatter);

        System.out.println("localDate is .." + localDate);
        return localDate;
    }


    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        System.out.println("CONVERTER TIMESTAMPS: " + localDateTime);
        return (localDateTime == null ? null : Timestamp.valueOf(localDateTime));
    }


}
