package com.ternobo.wallet.client.services;

import com.ternobo.wallet.client.records.ApplicationClient;
import com.ternobo.wallet.client.repositories.ApplicationClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ApplicationClientService {

    private final ApplicationClientRepository repository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public ApplicationClientService(ApplicationClientRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean deleteApplicationClient(String clientToken){
        return this.repository.deleteByClientToken(clientToken);
    }

    public ApplicationClient getApplicationClient(String clientToken){
        return this.repository.findByClientToken(clientToken).orElse(null);
    }

    public boolean validateApplicationClient(String clientToken, String clientSecret){
        ApplicationClient applicationClient = this.getApplicationClient(clientToken);
        if(applicationClient == null){
            return false;
        }
        return applicationClient.getClientSecret().contentEquals(this.passwordEncoder.encode(clientSecret));
    }

}
