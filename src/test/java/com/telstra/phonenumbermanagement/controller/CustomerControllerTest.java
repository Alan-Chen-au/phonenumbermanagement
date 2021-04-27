package com.telstra.phonenumbermanagement.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.telstra.phonenumbermanagement.common.Status;
import com.telstra.phonenumbermanagement.entity.Customer;
import com.telstra.phonenumbermanagement.entity.PhoneNumber;
import com.telstra.phonenumbermanagement.service.CustomerService;
import com.telstra.phonenumbermanagement.service.PhoneNumberService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
@ActiveProfiles("dev")
public class CustomerControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CustomerService customerService; 
	
	@MockBean
	private PhoneNumberService phoneNumberService; 
	
	private String name = "alex";
	private String email = "alex@telstra.com"; 
	private String number = "0412341234"; 
	private String number2 = "0412345678"; 
	private String number3 = "0411111111"; 
	
	@Test
	public void getCustomerTest() throws Exception {
		Customer c = getCustomer();
		when(customerService.findById(1L)).thenReturn(Optional.of(c)); 
		
		mvc.perform(get("/api/v1/customers/1"))
			  	.andExpect(status().is2xxSuccessful())
			  	.andDo(print()); 
		
		mvc.perform(get("/api/v1/customers/100"))
		.andExpect(status().is4xxClientError())
		.andDo(print()); 
	}
	
	@Test 
	public void getCustomer2Test() throws Exception {
		Customer c = getCustomer();
		when(customerService.findById(1L)).thenReturn(Optional.of(c)); 
		when(customerService.findByEmail(email)).thenReturn(Optional.of(c)); 
		
		mvc.perform(get("/api/v1/customers/").queryParam("queryField", "ids").queryParam("values", "1"))
			  	.andExpect(status().is2xxSuccessful())
			  	.andDo(print()); 
		
		mvc.perform(get("/api/v1/customers/").queryParam("queryField", "emails").queryParam("values", "alex@telstra.com"))
		.andExpect(status().is2xxSuccessful())
		.andDo(print()); 
		
		mvc.perform(get("/api/v1/customers/").queryParam("queryField", "emails").queryParam("values", "xxx@telstra.com"))
		.andExpect(status().is2xxSuccessful())
		.andDo(print()); 
		
		mvc.perform(get("/api/v1/customers/").queryParam("queryField", "XXX").queryParam("values", "alex@telstra.com"))
		.andExpect(status().isBadRequest())
		.andDo(print()); 
	}
	
	@Test
	public void activePhoneNumberTest() throws Exception {
		Customer c = getCustomer();
		PhoneNumber pn = getPendingStatusPhoneNumber(); 
		PhoneNumber pn2 = getActivedStatusPhoneNumber(); 
		when(customerService.findById(1L)).thenReturn(Optional.of(c)); 
		when(phoneNumberService.findByNumber(number)).thenReturn(Optional.of(pn)); 
		when(phoneNumberService.findByNumber(number2)).thenReturn(Optional.of(pn2)); 
		
		mvc.perform(patch("/api/v1/customers/{customerId}/phonenumbers/{number}", 1, number))
			  	.andExpect(status().is2xxSuccessful())
			  	.andDo(print()); 
		
		mvc.perform(patch("/api/v1/customers/{customerId}/phonenumbers/{number}", 1, number2))
		.andExpect(status().is4xxClientError())
		.andDo(print()); 
		
		mvc.perform(patch("/api/v1/customers/{customerId}/phonenumbers/{number}", 1, number2))
		.andExpect(status().is4xxClientError())
		.andDo(print()); 
		
		mvc.perform(patch("/api/v1/customers/{customerId}/phonenumbers/{number}", 1, number3))
		.andExpect(status().is4xxClientError())
		.andDo(print()); 
	}
	
	private Customer getCustomer() {
		Customer c = new Customer();
		c.setId(1L);
		c.setName(name);
		c.setEmail(email);
		return c;
	}
	
	private PhoneNumber getPendingStatusPhoneNumber() {
		PhoneNumber pn = new PhoneNumber(); 
		pn.setId(1L);
		pn.setNumber(number);
		pn.setStatus(Status.PENDING);
		
		return pn;
	}
	
	private PhoneNumber getActivedStatusPhoneNumber() {
		PhoneNumber pn = new PhoneNumber(); 
		pn.setId(1L);
		pn.setNumber(number2);
		pn.setStatus(Status.ACTIVED);
		
		return pn;
	}

}
