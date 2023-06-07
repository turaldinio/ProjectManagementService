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
        logger.info("create method has started");

        checkCredentialRequiredFields(createCredentialDto);//проверка наличия обязательных полей login/password

        checkCredentialLoginAlreadyExists(createCredentialDto);//проверка свободен ли логин

        var credential = credentialMapping.create(createCredentialDto);

        credentialRepository.save(credential);

        logger.info("credential has been saved");

        return credential;
    }

    public Credential update(Credential oldCredential, CreateCredentialDto newCredentialDto) {
        logger.info("update method has started");

        var result = credentialMapping.update(oldCredential, newCredentialDto);
        logger.info("credentials have been updated");

        return result;
    }

    public CredentialDto mapCredentialToCredentialDto(Credential credential) {
        logger.info("mapCredentialToCredentialDto method has started");

        if (Objects.isNull(credential)) {
            return null;
        }
        var result = credentialMapping.map(credential);
        logger.info("mapping credential -> credentialDto");

        return result;
    }

    public void checkCredentialRequiredFields(CreateCredentialDto credentialDto) {
        logger.info("credential verification required fields");

        if (Objects.isNull(credentialDto.getLogin()) ||
                Objects.isNull(credentialDto.getPassword())) {
            logger.info("canceling of the operation");

            throw new BadRequest("the login or password cannot be null or blank");
        }
    }


    public void checkCredentialLoginAlreadyExists(CreateCredentialDto credentialDto) {
        logger.info("credential checking the uniqueness of the account");

        if (credentialRepository.existsByLogin(credentialDto.getLogin())) {//проверяем что login свободен
            logger.info("canceling of the operation");

            throw new BadRequest(String.format("the %s login already exists", credentialDto.getLogin()));
        }
    }
}
