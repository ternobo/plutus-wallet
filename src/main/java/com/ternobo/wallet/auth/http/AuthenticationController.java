package com.ternobo.wallet.auth.http;

import com.ternobo.wallet.auth.records.JWTResponse;
import com.ternobo.wallet.auth.requests.UserLogin;
import com.ternobo.wallet.auth.service.JWTService;
import com.ternobo.wallet.user.records.User;
import com.ternobo.wallet.utils.http.RestfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("login")
    public RestfulResponse<JWTResponse> login(@Valid @RequestBody UserLogin login){
        Authentication authenticate = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.username(), login.password()));
        if(authenticate.isAuthenticated()){
            return new RestfulResponse<>(true, jwtService.generateToken(authenticate.getName()));
        }
        return new RestfulResponse<>(false);
    }

}
