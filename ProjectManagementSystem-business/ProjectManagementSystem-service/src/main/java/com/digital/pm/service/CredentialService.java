package com.digital.pm.service;

import com.digital.pm.dto.credential.CreateCredentialDto;
import com.digital.pm.dto.credential.CredentialDto;
import com.digital.pm.model.Credential;

public interface CredentialService {
    Credential create(CreateCredentialDto createCredentialDto);
    Credential update(Credential oldCredential, CreateCredentialDto newCredentialDto);
    CredentialDto mapCredentialToCredentialDto(Credential credential);

}
