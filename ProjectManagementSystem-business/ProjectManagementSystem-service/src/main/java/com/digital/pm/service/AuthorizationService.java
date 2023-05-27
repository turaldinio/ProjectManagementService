package com.digital.pm.service;

import com.digital.pm.common.auth.AuthorizationRequest;

public interface AuthorizationService {
    String authorize(AuthorizationRequest authorizationRequest);
}
