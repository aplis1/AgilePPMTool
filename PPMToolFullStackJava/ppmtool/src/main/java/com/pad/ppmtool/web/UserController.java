package com.pad.ppmtool.web;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pad.ppmtool.domain.User;
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
