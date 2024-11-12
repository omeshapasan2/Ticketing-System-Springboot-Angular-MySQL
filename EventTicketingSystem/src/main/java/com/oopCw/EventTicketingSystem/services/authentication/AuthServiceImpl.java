package com.oopCw.EventTicketingSystem.services.authentication;

import com.oopCw.EventTicketingSystem.dto.SignupRequestDTO;
import com.oopCw.EventTicketingSystem.entity.User;
import com.oopCw.EventTicketingSystem.enums.UserRole;
import com.oopCw.EventTicketingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    public UserDto signupClient(SignupRequestDTO signupRequestDTO){
        User user = new User();

        user.setName(signupRequestDTO.getName());
        user.setLastname(signupRequestDTO.getLastname());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(signupRequestDTO.getPassword());

        user.setRole(UserRole.CUSTOMER);

    }
}