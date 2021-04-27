package com.telstra.phonenumbermanagement.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.telstra.phonenumbermanagement.common.CustomerType;
import com.telstra.phonenumbermanagement.common.MobilePlan;
import com.telstra.phonenumbermanagement.entity.Customer;
import com.telstra.phonenumbermanagement.entity.PhoneNumber;
import com.telstra.phonenumbermanagement.repository.CustomerRepository;
import com.telstra.phonenumbermanagement.service.CustomerService;

@SpringBootTest
public class CustomerServiceImplTest {
	
	@Mock
	private CustomerRepository customerRepository; 
	
	@InjectMocks
	private CustomerService customerServie = new CustomerServiceImpl(); 
	
	private String name = "alex";
	private String email = "alex@telstra.com"; 
	
	@BeforeEach
	public void setMockOutput() {
		PhoneNumber pn = getPhoneNumber(); 
		Customer c = new Customer(); 
		c.setId(1L);
		c.setName(name);
		c.setEmail(email);
		c.setAddress("Dandenong Rd");
		c.setSuburb("Caufield South");
		c.setState("vic");
		c.setPostcode("3126");
		c.setType(CustomerType.BROWN);
		c.addPhoneNumber(pn);
		c.removePhoneNumber(pn);
		c.setNumbers(null);
		c.setAddedDate(LocalDate.now());
		c.toString();
		
		when(customerServie.findById(1L)).thenReturn(Optional.of(c));
		when(customerServie.findByEmail(email)).thenReturn(Optional.of(c));
		when(customerServie.save(Mockito.any(Customer.class))).thenReturn(c);
	}
	
	@Test
	public void findByIdTest() {
		Optional<Customer> optional = customerServie.findById(1L); 
		assertEquals(name, optional.get().getName()); 
	}
	
	@Test
	public void findByEmailTest() {
		Optional<Customer> optional = customerServie.findByEmail(email); 
		assertEquals(email, optional.get().getEmail()); 
	}
	
	@Test
	public void saveTest() {
		Customer c = customerServie.save(new Customer()); 
		assertEquals(1, c.getId()); 
		assertEquals(name, c.getName()); 
	}
	
	private PhoneNumber getPhoneNumber() {
		PhoneNumber pn = new PhoneNumber(); 
		pn.setId(1L);
		pn.setNumber("0412341234");
		pn.setImei("abcd1234esllsl");
		pn.setPlan(MobilePlan.BUSINESS);
		pn.setAddedDate(LocalDate.now());
		pn.toString();
		
		return pn;
	}

}
