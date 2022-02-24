package com.ternobo.wallet.auth.service;

import com.ternobo.wallet.auth.records.AccessToken;
import com.ternobo.wallet.auth.repositories.AccessTokenRepository;
import com.ternobo.wallet.user.records.User;
import com.ternobo.wallet.user.service.UserService;
import com.ternobo.wallet.utils.SecureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AccessTokenService {

    public final static int tokenExpirationDays = 10;

    private final AccessTokenRepository repository;
    private final UserService userService;


    @Autowired
    public AccessTokenService(AccessTokenRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public AccessToken findByRefreshToken(String refreshToken) {
        return this.repository.findByRefreshToken(refreshToken).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Optional<AccessToken> findById(Long id) {
        return this.repository.findById(id);
    }

    public AccessToken createAccessToken(User user) {
        return this.repository.save(new AccessToken(this.generateRefreshToeken(), user, LocalDate.now().plusDays(AccessTokenService.tokenExpirationDays)));
    }

    public AccessToken createAccessToken(String username) {
        return this.repository.save(new AccessToken(this.generateRefreshToeken(), this.userService.findByUsername(username), LocalDate.now().plusDays(AccessTokenService.tokenExpirationDays)));
    }

    public void deleteAccessToken(Long id) {
        this.repository.deleteById(id);
    }

    public void deleteAccessToken(AccessToken accessToken) {
        this.repository.deleteById(accessToken.getId());
    }

    public String generateRefreshToeken() {
        return SecureUtils.generateRandomString(32, s -> !this.repository.existsByRefreshToken(s));
    }

}
