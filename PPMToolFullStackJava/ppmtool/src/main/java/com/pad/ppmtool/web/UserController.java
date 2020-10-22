package com.pad.ppmtool.web;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pad.ppmtool.domain.User;
import com.pad.ppmtool.payload.JWTLoginSucessReponse;
import com.pad.ppmtool.payload.LoginRequest;
import com.pad.ppmtool.security.JwtTokenProvider;
import com.pad.ppmtool.security.SecurityConstants;
import com.pad.ppmtool.services.MapValidationErrorService;
import com.pad.ppmtool.services.UserService;
import com.pad.ppmtool.validate.UserValidator;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	UserValidator validator;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if(errorMap != null) {
			return errorMap;
		}
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> saveUser(@Valid @RequestBody User user, BindingResult result){
		
		
		validator.validate(user, result);
		
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if(errorMap != null) {
			return errorMap;
		}
		
		User newUser = userService.saveUser(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
		
	}

}
