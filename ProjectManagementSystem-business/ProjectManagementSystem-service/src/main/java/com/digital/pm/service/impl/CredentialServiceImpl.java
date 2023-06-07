package com.digital.pm.service.impl;

import com.digital.pm.dto.credential.CreateCredentialDto;
import com.digital.pm.dto.credential.CredentialDto;
import com.digital.pm.model.Credential;
import com.digital.pm.repository.CredentialRepository;
import com.digital.pm.service.CredentialService;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.CredentialMapping;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CredentialServiceImpl implements CredentialService {
    private final CredentialRepository credentialRepository;
    private final CredentialMapping credentialMapping;
    @Autowired
    @Qualifier("credentialLogger")
    private Logger logger;

    public Credential create(CreateCredentialDto createCredentialDto) {

        checkCredentialRequiredFields(createCredentialDto);//проверка наличия обязательных полей login/password

        checkCredentialLoginAlreadyExists(createCredentialDto);//проверка свободен ли логин

        var credential = credentialMapping.create(createCredentialDto);

        credentialRepository.save(credential);

        logger.info(String.format("credential %s is created",credential));

        return credential;
    }

    public Credential update(Credential oldCredential, CreateCredentialDto newCredentialDto) {
        return credentialMapping.update(oldCredential, newCredentialDto);
    }
    public CredentialDto mapCredentialToCredentialDto(Credential credential) {
        if (Objects.isNull(credential)) {
            return null;
        }
        return credentialMapping.map(credential);
    }

    public void checkCredentialRequiredFields(CreateCredentialDto credentialDto) {
        if (Objects.isNull(credentialDto.getLogin()) ||
                Objects.isNull(credentialDto.getPassword())) {
            throw new BadRequest("the login or password cannot be null or blank");
        }
    }


    public void checkCredentialLoginAlreadyExists(CreateCredentialDto credentialDto) {
        if (credentialRepository.existsByLogin(credentialDto.getLogin())) {//проверяем что login свободен
            throw new BadRequest(String.format("the %s login already exists", credentialDto.getLogin()));
        }
    }
}
