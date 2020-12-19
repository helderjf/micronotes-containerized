package com.hfaria.micronotesback.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hfaria.micronotesback.authentication.dto.LoginRequestDTO;
import com.hfaria.micronotesback.authentication.dto.LoginResponseDTO;
import com.hfaria.micronotesback.authentication.dto.RegistryRequestDTO;
import com.hfaria.micronotesback.authentication.service.AuthenticationService;
import com.hfaria.micronotesback.model.User;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authService;

	@GetMapping("/hello")
	public String hello() {
		return "Hello World!";
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegistryRequestDTO registryRequest) {
		if (authService.registerNewUser(registryRequest)) {
			return new ResponseEntity<Object>(HttpStatus.OK);
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/login")
	public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
	    String token = authService.login(loginRequest);
	    User user = getCurrentUser();
	    LoginResponseDTO response = new LoginResponseDTO();
	    response.fullName = user.getFullName();
	    response.email = user.getEmail();
	    response.authenticationToken = token;
	    
	    return response;
	}
	
    private User getCurrentUser() {
        User user = authService.getCurrentUser()
                .orElseThrow(() -> new IllegalArgumentException("No user logged in."));
        return user;
    }
	

}
