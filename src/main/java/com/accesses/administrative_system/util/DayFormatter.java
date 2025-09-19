package com.accesses.administrative_system.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class DayFormatter {

    public static LocalDate formatDay(String day){

        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return LocalDate.parse(day, myFormat);

    }

}
