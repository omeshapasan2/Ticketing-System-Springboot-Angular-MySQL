package com.oopCw.EventTicketingSystem.controller;

import com.oopCw.EventTicketingSystem.dto.SignupRequestDTO;
import com.oopCw.EventTicketingSystem.dto.UserDto;
import com.oopCw.EventTicketingSystem.services.authentication.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthService authService;

    @PostMapping("/customer/signup")
    public ResponseEntity<?> signupClient(@RequestBody SignupRequestDTO signupRequestDTO){
        if(authService.precentByEmail(signupRequestDTO.getEmail())){
            return new ResponseEntity<>("Client Already Exists" , HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto createdUser = authService.signupClient(signupRequestDTO);

        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @PostMapping("/vendor/signup")
    public ResponseEntity<?> signupVendor(@RequestBody SignupRequestDTO signupRequestDTO){
        if(authService.precentByEmail(signupRequestDTO.getEmail())){
            return new ResponseEntity<>("Vendor Already Exists" , HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto createdUser = authService.signupClient(signupRequestDTO);

        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }
}
