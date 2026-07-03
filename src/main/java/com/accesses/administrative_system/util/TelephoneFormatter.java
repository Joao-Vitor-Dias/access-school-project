package com.accesses.administrative_system.util;

import org.springframework.stereotype.Component;

@Component
public abstract class TelephoneFormatter {

    public static String telephoneFormatter(String telephone){

        String digitsOnly = telephone.replaceAll("[^0-9]", "");

        return "55" + digitsOnly;

    }


}
