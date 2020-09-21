package com.poc.spring.openldap.controller;

import com.poc.spring.openldap.dto.UserDTO;
import com.poc.spring.openldap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.MediaType;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/authenticate", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO authenticate() {
        return userService.authenticate("maintainer", "maintainer_pass");
    }

    @GetMapping(value = "/groups", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getUserGroups() {
        return userService.getUserGroups("maintainer");
    }
}
