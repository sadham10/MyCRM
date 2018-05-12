package com.hussain.mycrm.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hussain.mycrm.model.User;
import com.hussain.mycrm.service.UserService;


@CrossOrigin(origins={"http://localhost:4200","http://localhost:4201"},allowCredentials="true",allowedHeaders="true")
@RequestMapping(value="/api")
@RestController
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value="/createUser", produces={MediaType.TEXT_PLAIN_VALUE})
	public User createUser(User user,String password) {
		try {
			return userService.createUser(user,password);
		} catch(Exception e) {
			LOGGER.error("Error in sendReports of AdminController:",e);
			return null;
		}
	}

	@RequestMapping(value="/mockUser", produces={MediaType.TEXT_PLAIN_VALUE})
	public boolean sendReports () {
		try {
			LOGGER.info("welcome everyone!!");
			String password = "sadham";
			User user=new User();
			user.setActive(true);
			user.setMobileNumber(9176762);
			user.setUserName("hussain");
			userService.createUser(user,password);
			return true;
		} catch(Exception e) {
			LOGGER.error("Error in sendReports of AdminController:",e);
		}
		return false;
	}



}
