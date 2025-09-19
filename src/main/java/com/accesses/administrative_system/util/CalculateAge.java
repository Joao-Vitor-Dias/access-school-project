package com.accesses.administrative_system.util;

import java.time.LocalDate;
import java.time.Period;

public abstract class CalculateAge {

    public static int calculateAge(LocalDate birthDay){

        LocalDate today = LocalDate.now();

        return Period.between(birthDay,today).getYears();

    }

    public static boolean verifyIsLegal(LocalDate birthDay){

        return calculateAge(birthDay) >= 18;

    }

}
