package com.hussain.mycrm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hussain.mycrm.model.Customer;
import com.hussain.mycrm.model.User;
import com.hussain.mycrm.service.CustomerService;
import com.hussain.mycrm.service.UserService;

@CrossOrigin(origins={"http://localhost:4200","http://localhost:4201"},allowCredentials="true",allowedHeaders="true")
@RequestMapping(value="/api")
@RestController
public class CustomerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;


	

	

	@RequestMapping(value="/getAllCustomers", produces={MediaType.APPLICATION_JSON_VALUE},method=RequestMethod.GET)
	public List<Customer> getAllCustomer(HttpServletRequest request) {

		try {
			LOGGER.info("inside getAllCustomer()!!");
			return customerService.getAllCustomers();
		} catch(Exception e) {
			LOGGER.error("Error while retrieving cutomer: ",e);	
		}
		return null;
	}

	@RequestMapping(value="/saveCustomer",method=RequestMethod.POST)
	public Customer saveCustomer(@RequestBody Customer customer){
		try{
			
			return customer=customerService.saveCustomer(customer);
		}catch(Exception e){
			LOGGER.error("Error while saving cutomer: ",e);	
		}
		return null;
	}



}

