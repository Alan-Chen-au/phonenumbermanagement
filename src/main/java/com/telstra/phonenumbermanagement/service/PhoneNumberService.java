package com.telstra.phonenumbermanagement.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.telstra.phonenumbermanagement.common.Status;
import com.telstra.phonenumbermanagement.entity.PhoneNumber;

public interface PhoneNumberService {
	
	public Optional<PhoneNumber> findById(Long id); 
	
	public Optional<PhoneNumber> findByNumber(String number); 
	
	public PhoneNumber save(PhoneNumber number);
	
	public Page<PhoneNumber> findAll(int pageNumber, int count); 
	
	public Page<PhoneNumber> findAllByStatus(Status status, int pageNumber, int count); 

}
