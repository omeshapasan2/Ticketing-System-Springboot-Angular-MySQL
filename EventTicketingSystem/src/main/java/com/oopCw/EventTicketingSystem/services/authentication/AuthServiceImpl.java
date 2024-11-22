package com.oopCw.EventTicketingSystem.services.authentication;

import com.oopCw.EventTicketingSystem.dto.SignupRequestDTO;
import com.oopCw.EventTicketingSystem.dto.UserDto;
import com.oopCw.EventTicketingSystem.entity.User;
import com.oopCw.EventTicketingSystem.enums.UserRole;
import com.oopCw.EventTicketingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));

        user.setRole(UserRole.CUSTOMER);

        return userRepository.save(user).getDto();

    }

    public Boolean precentByEmail(String email){
        return userRepository.findFirstByEmail(email) != null;
    }

    public UserDto signupVendor(SignupRequestDTO signupRequestDTO){
        User user = new User();

        user.setName(signupRequestDTO.getName());
        user.setLastname(signupRequestDTO.getLastname());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));

        user.setRole(UserRole.VENDOR);

        return userRepository.save(user).getDto();

    }
}
