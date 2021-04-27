package com.telstra.phonenumbermanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.telstra.phonenumbermanagement.common.QueryField;
import com.telstra.phonenumbermanagement.common.Status;
import com.telstra.phonenumbermanagement.entity.Customer;
import com.telstra.phonenumbermanagement.entity.PhoneNumber;
import com.telstra.phonenumbermanagement.service.CustomerService;
import com.telstra.phonenumbermanagement.service.PhoneNumberService;

import io.swagger.annotations.ApiOperation;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService; 
	
	@Autowired
	private PhoneNumberService phoneNumberService; 
	
	@GetMapping("/api/v1/customers/{id}")
	@ApiOperation(value = "Find a customer by id", 
	  			notes = "Provide an id to look up specifice customer from the Phone Number Management", 
	  			response = Customer.class)
	public Customer getCustomer( @PathVariable Long id, HttpServletResponse response ) {
		Customer c = getCustomer(id); 
		return c; 
	}
	
	@GetMapping("/api/v1/customers")
	@ApiOperation(value = "Find customers by ids or emails", 
				notes = "Provide a list of ids or emails to look up specifice Customers from the Phone Number Management", 
				response = Customer.class)
	public List<Customer> getCustomer(@RequestParam(required = true) QueryField queryField, 
											@RequestParam(required = true) List<String> values) {
		
		if (queryField == QueryField.ids) {
			return getCustomersByIds(values);
		} else if (queryField == QueryField.emails) {
			return getCustomers(values); 
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("We do not support queryField (%s). ", queryField));
		}
	}
	
	@PatchMapping("/api/v1/customers/{customerId}/phonenumbers/{number}")
	@ApiOperation(value = "Active a number for a customer", 
			notes = "Active a phone number for a existing customer. The status of the phone number must be PENDING. ", 
			response = String.class)
	public String activePhoneNumber( @PathVariable Long customerId, @PathVariable String number, HttpServletResponse response ) {
		
		Customer customer = getCustomer(customerId); 
		
		PhoneNumber phoneNumber = getPhoneNumber(number); 
		
		// to active a number; the status for this number must be PENDING. 
		if (phoneNumber.getStatus() != Status.PENDING) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					String.format("The status for this Phone Number MUST be PENDING. Current status is (%s)", phoneNumber.getStatus()));
		} 
		
		// update phone number status
		phoneNumber.setStatus(Status.ACTIVED); 
		phoneNumber.setCustomer(customer); 
		phoneNumberService.save(phoneNumber);
		
		// attach this phone number to this customer 
		customer.getNumbers().add(phoneNumber); 
		customerService.save(customer); 
		
		return number + " has been actived! "; 
	}
	
	private Customer getCustomer(Long id) {
		Optional<Customer> optional = customerService.findById(id); 
		if (optional.isPresent()) {
			return optional.get(); 
		} 
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No Customer found for id (%s)", id));
	}
	
	private List<Customer> getCustomersByIds(List<String> ids) {
		List<Customer> customers = new ArrayList<>(); 
		Optional<Customer> optional = null; 
		
		if (ids != null && ids.size() > 0) {
			for (String id : ids) {
				if (id != null && id.length() > 0) {
					optional = customerService.findById(Long.valueOf(id)); 
					if (optional.isPresent()) {
						customers.add(optional.get()); 
					} 
				}
			}
		}
		
		return customers; 
	}
	
	private List<Customer> getCustomers(List<String> emails) {
		List<Customer> customers = new ArrayList<>(); 
		Optional<Customer> optional = null; 
		
		if (emails != null && emails.size() > 0) {
			for (String email : emails) {
				optional = customerService.findByEmail(email); 
				if (optional.isPresent()) {
					customers.add(optional.get()); 
				} 
			}
		}
		
		return customers; 
	}
	
	private PhoneNumber getPhoneNumber(String number) {
		Optional<PhoneNumber> optional = phoneNumberService.findByNumber(number); 
		if (optional.isPresent()) {
			return optional.get(); 
		} 
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No Phone Number found for number (%s)", number));
	}
	
}
