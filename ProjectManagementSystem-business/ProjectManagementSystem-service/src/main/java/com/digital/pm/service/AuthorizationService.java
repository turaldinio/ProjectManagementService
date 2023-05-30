package com.digital.pm.service;

import com.digital.pm.service.auth.AuthorizationRequest;
import com.digital.pm.service.auth.AuthorizationResponse;

public interface AuthorizationService {
    AuthorizationResponse authorize(AuthorizationRequest authorizationRequest);
}
