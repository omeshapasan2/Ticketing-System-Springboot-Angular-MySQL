package com.oopCw.EventTicketingSystem.services.authentication;

import com.oopCw.EventTicketingSystem.dto.SignupRequestDTO;
import com.oopCw.EventTicketingSystem.dto.UserDto;

public interface AuthService {
    UserDto signupClient(SignupRequestDTO signupRequestDTO);
    UserDto signupVendor(SignupRequestDTO signupRequestDTO);
    Boolean precentByEmail(String email);
}
