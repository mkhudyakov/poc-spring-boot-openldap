package com.poc.spring.openldap.service;

import com.poc.spring.openldap.dto.UserDTO;
import com.poc.spring.openldap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO authenticate(String name, String password) {
        return userRepository.authenticate(name, password).map(result -> {
            UserDTO dto = new UserDTO();
            dto.setGivenName(result[0]);
            dto.setDisplayName(result[1]);
            return dto;
        }).orElse(null);
    }

    public List<String> getUserGroups(String name) {
        return userRepository.getUserGroups(name);
    }
}
