package com.telstra.phonenumbermanagement.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.telstra.phonenumbermanagement.common.Status;
import com.telstra.phonenumbermanagement.entity.PhoneNumber;
import com.telstra.phonenumbermanagement.service.PhoneNumberService;

@RestController
public class PhoneNumberController {
	
	private static final int MAX_COUNT = 5; 
	
	@Autowired
	private PhoneNumberService phoneNumberService; 
	
	@GetMapping("/api/v1/phonenumbers/{id}")
	public PhoneNumber getPhoneNumber(@PathVariable Long id, HttpServletResponse response) {
		return getPhoneNumber(id);
	}
	
	@GetMapping("/api/v1/phonenumbers")
	public List<PhoneNumber> getPhoneNumbers(@RequestParam(required = false) Status status, 
											@RequestParam(required = true) int pageNumber, 
											@RequestParam(required = true) int count) {
		
		if (count > MAX_COUNT) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("The MAX count is %s", MAX_COUNT));
		}
		
		if (pageNumber < 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The page number must be >= 0. ");
		}
		
		if (status == null) {
			return phoneNumberService.findAll(pageNumber, count).getContent(); 
		} else {
			return phoneNumberService.findAllByStatus(status, pageNumber, count).getContent(); 
		}
	}
	
	private PhoneNumber getPhoneNumber(Long id) {
		Optional<PhoneNumber> optional = phoneNumberService.findById(id); 
		if (optional.isPresent()) {
			return optional.get(); 
		} 
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No Phone Number found for id (%s)", id));
	}
}
