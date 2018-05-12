package com.hussain.mycrm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hussain.mycrm.model.Customer;

@Service
public interface CustomerService {
	
public List<Customer> getAllCustomers();
	
	public Customer saveCustomer(Customer customer);

}
