package com.digital.pm.repository.spec;

import org.springframework.stereotype.Component;

@Component
public class FilterHandler {
    public static String getFormatedString(String line) {
        return "%" + line.toLowerCase() + "%";
    }
}
