package com.ternobo.wallet.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ternobo.wallet.auth.records.AccessToken;
import com.ternobo.wallet.user.records.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class JTWService {

    @Value("${jwt.secret}")
    private String secret;

    private AccessTokenService accessTokenService;

    @Autowired
    public JTWService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    public DecodedJWT validateJWT(String token) {
        Algorithm algorithmHS = Algorithm.HMAC512(this.secret);
        JWTVerifier verifier = JWT.require(algorithmHS)
                .withIssuer("ternobo")
                .build();
        return verifier.verify(token);
    }

    public String generateToken(User userDetails) {
        Algorithm algorithmHS = Algorithm.HMAC512(this.secret);
        AccessToken refreshToken = this.accessTokenService.createAccessToken(userDetails);
        return JWT.create()
                .withIssuer("ternobo")
                .withSubject(String.valueOf(userDetails.getId()))
                .withIssuedAt(new Date())
                .withExpiresAt(java.sql.Date.valueOf(LocalDate.now().plusDays(10)))
                .withClaim("refreshToken", refreshToken.getRefreshToken())
                .withClaim("preferred_username", userDetails.getUsername())
                .withClaim("name", userDetails.getName())
                .withJWTId(String.valueOf(refreshToken.getId()))
                .sign(algorithmHS);
    }
}
