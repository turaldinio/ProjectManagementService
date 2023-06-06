package com.digital.pm.service.mapping;

import com.digital.pm.dto.credential.CreateCredentialDto;
import com.digital.pm.dto.credential.CredentialDto;
import com.digital.pm.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CredentialMapping {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Credential create(CreateCredentialDto credentialDto) {

        return Credential.
                builder().
                login(credentialDto.getLogin()).
                password(bCryptPasswordEncoder.encode(credentialDto.getPassword())).
                build();
    }


    public Credential update(Credential credential, CreateCredentialDto credentialDto) {
        if (credentialDto.getLogin() != null) {
            credential.setLogin(credential.getLogin());
        }
        if (credentialDto.getPassword() != null) {
            credential.setPassword(credential.getPassword());
        }
        return credential;
    }

    public CredentialDto map(Credential credential) {
        return CredentialDto.
                builder().
                login(credential.getLogin()).
                build();
    }

}
