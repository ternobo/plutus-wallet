package com.ternobo.wallet.auth.service;

import com.ternobo.wallet.auth.records.AccessToken;
import com.ternobo.wallet.auth.repositories.AccessTokenRepository;
import com.ternobo.wallet.user.records.User;
import com.ternobo.wallet.utils.SecureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccessTokenService {

    private final AccessTokenRepository repository;

    @Autowired
    public AccessTokenService(AccessTokenRepository repository) {
        this.repository = repository;
    }

    public AccessToken findByRefreshToken(String refreshToken) {
        return this.repository.findByRefreshToken(refreshToken).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public AccessToken createAccessToken(User user) {
        return this.repository.save(new AccessToken(this.generateRefreshToeken(), user));
    }

    public String generateRefreshToeken() {
        return SecureUtils.generateRandomString(32, s -> !this.repository.existsByRefreshToken(s));
    }

}
