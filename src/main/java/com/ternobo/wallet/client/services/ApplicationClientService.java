package com.ternobo.wallet.client.services;

import com.ternobo.wallet.client.records.ApplicationClient;
import com.ternobo.wallet.client.records.ApplicationClientWithSecret;
import com.ternobo.wallet.client.repositories.ApplicationClientRepository;
import com.ternobo.wallet.utils.SecureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationClientService {

    public final PasswordEncoder passwordEncoder;
    private final ApplicationClientRepository repository;

    @Autowired
    public ApplicationClientService(ApplicationClientRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isAnyClientExists() {
        return this.repository.findAll().size() > 0;
    }

    public ApplicationClientWithSecret createApplicationClient(String clientName) {
        String clientToken = SecureUtils.generateRandomString(32, s -> this.getApplicationClient(s) == null);
        String clientSecret = SecureUtils.generateRandomString(32);
        ApplicationClient appClient = ApplicationClient.builder()
                .clientSecret(this.passwordEncoder.encode(clientSecret))
                .clientToken(clientToken)
                .name(clientName)
                .build();
        return new ApplicationClientWithSecret(this.repository.save(appClient), clientSecret);
    }

    public boolean deleteApplicationClient(String clientToken) {
        return this.repository.deleteByClientToken(clientToken);
    }

    public ApplicationClient getApplicationClient(String clientToken) {
        return this.repository.findByClientToken(clientToken).orElse(null);
    }

    public boolean validateApplicationClient(String clientToken, String clientSecret) {
        ApplicationClient applicationClient = this.getApplicationClient(clientToken);
        if (applicationClient == null) {
            return false;
        }
        return this.passwordEncoder.matches(clientSecret, applicationClient.getClientSecret());
    }

}
