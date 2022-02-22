package com.ternobo.wallet.user.http.controllers;

import com.ternobo.wallet.user.http.requests.UserDTO;
import com.ternobo.wallet.user.records.User;
import com.ternobo.wallet.user.service.UserService;
import com.ternobo.wallet.utils.http.RestfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RestfulResponse<User> createUser(@Valid @RequestBody UserDTO request) {
        User user = this.service.createUser(request);
        return new RestfulResponse<>(true, user, request.toString());
    }
}
