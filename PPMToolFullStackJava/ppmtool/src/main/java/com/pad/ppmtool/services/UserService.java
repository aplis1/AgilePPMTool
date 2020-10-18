package com.pad.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pad.ppmtool.domain.User;
import com.pad.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.pad.ppmtool.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCyrptPasswordEncoder;
	
	public User saveUser(User newUser) {
		
		try {
            newUser.setPassword(bCyrptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword
            newUser.setConfirmPassword("");
            User dupUser = userRepository.findByUsername(newUser.getUsername());
            if(dupUser == null) {
            	 return userRepository.save(newUser);
            }
           
			
		}catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists");
		}
		return newUser;

		
	}
	

}
