package com.pad.ppmtool.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.pad.ppmtool.domain.User;

@Component
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User)target;
		
		if(user.getPassword().length() < 6) {
			errors.rejectValue("password","Length", "Password must be at least 6 characters");
		}
		
		if(!user.getConfirmPassword().equals(user.getPassword())){
			errors.rejectValue("confirmPassword","Match", "Password must macth");
		}
		
	}

}
