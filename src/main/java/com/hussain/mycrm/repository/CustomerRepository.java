package com.hussain.mycrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hussain.mycrm.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	
}
