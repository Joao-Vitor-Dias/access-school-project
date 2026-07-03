package com.accesses.administrative_system.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public abstract class CalculateAge {

    public static int calculateAge(LocalDate birthDay){

        LocalDate today = LocalDate.now();

        return Period.between(birthDay,today).getYears();

    }

    public static boolean verifyIsLegal(LocalDate birthDay){

        return calculateAge(birthDay) >= 18;

    }

}
