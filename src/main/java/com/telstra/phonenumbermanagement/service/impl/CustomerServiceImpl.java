package com.telstra.phonenumbermanagement.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telstra.phonenumbermanagement.entity.Customer;
import com.telstra.phonenumbermanagement.repository.CustomerRepository;
import com.telstra.phonenumbermanagement.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository; 

	@Override
	public Optional<Customer> findById(Long id) {
		return customerRepository.findById(id);
	}
	
	@Override
	public Optional<Customer> findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}
}
