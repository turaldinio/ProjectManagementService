package com.digital.pm.repository.spec;

import org.springframework.stereotype.Component;

@Component
public class SpecHandler {
    public static String getFormatedString(String line) {
        return "%" + line.toLowerCase() + "%";
    }
}
