package com.digital.pm.service.exceptions;

import lombok.Data;

public class BadRequest extends RuntimeException {

    public BadRequest(String message) {
        super(message);
    }

}
