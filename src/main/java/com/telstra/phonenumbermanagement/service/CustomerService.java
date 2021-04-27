package com.telstra.phonenumbermanagement.service;

import java.util.Optional;

import com.telstra.phonenumbermanagement.entity.Customer;

public interface CustomerService {
	
	public Optional<Customer> findById(Long id); 
	
	public Optional<Customer> findByEmail(String email); 
	
	public Customer save(Customer customer);

}
