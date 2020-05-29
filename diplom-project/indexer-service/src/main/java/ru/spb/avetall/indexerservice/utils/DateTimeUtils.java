package ru.spb.avetall.indexerservice.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public abstract class DateTimeUtils {

    public static LocalDateTime toLocalDateTime(String creationDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            return LocalDateTime.parse(creationDate, formatter);
        } catch (Exception ex) {
            return LocalDateTime.now();
        }
    }

}
