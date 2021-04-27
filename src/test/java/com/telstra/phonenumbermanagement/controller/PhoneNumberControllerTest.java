package com.telstra.phonenumbermanagement.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.telstra.phonenumbermanagement.common.Status;
import com.telstra.phonenumbermanagement.entity.PhoneNumber;
import com.telstra.phonenumbermanagement.service.PhoneNumberService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PhoneNumberController.class)
@ActiveProfiles("dev")
public class PhoneNumberControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PhoneNumberService phoneNumberService; 
	
	@Test
	public void getPhoneNumberTest() throws Exception {
		PhoneNumber pn = getPhoneNumber();
		when(phoneNumberService.findById(1L)).thenReturn(Optional.of(pn)); 
		
		mvc.perform(get("/api/v1/phonenumbers/1"))
			  	.andExpect(status().is2xxSuccessful())
			  	.andDo(print()); 
		
		mvc.perform(get("/api/v1/phonenumbers/100"))
		.andExpect(status().is4xxClientError())
		.andDo(print()); 
	}
	
	@Test
	public void getPhoneNumbersTest() throws Exception {
		PhoneNumber pn = getPhoneNumber();
		when(phoneNumberService.findAll(0, 5)).thenReturn(new PageImpl<PhoneNumber>(Arrays.asList(pn))); 
		when(phoneNumberService.findAllByStatus(Status.PENDING, 0, 5)).thenReturn(new PageImpl<PhoneNumber>(Arrays.asList(pn))); 
		
		mvc.perform(get("/api/v1/phonenumbers/"))
		.andExpect(status().isBadRequest())
		.andDo(print()); 
		
		mvc.perform(get("/api/v1/phonenumbers/").queryParam("pageNumber", "-2").queryParam("count", "3"))
		.andExpect(status().isBadRequest())
		.andDo(print()); 
		
		mvc.perform(get("/api/v1/phonenumbers/").queryParam("pageNumber", "0").queryParam("count", "10"))
		.andExpect(status().isBadRequest())
		.andDo(print()); 
		
		mvc.perform(get("/api/v1/phonenumbers/").queryParam("pageNumber", "0").queryParam("count", "5"))
		.andExpect(status().is2xxSuccessful())
		.andDo(print()); 
		
		mvc.perform(get("/api/v1/phonenumbers/").queryParam("pageNumber", "0").queryParam("count", "5").queryParam("status", "PENDING"))
		.andExpect(status().is2xxSuccessful())
		.andDo(print()); 
	}
	
	private PhoneNumber getPhoneNumber() {
		PhoneNumber pn = new PhoneNumber(); 
		pn.setId(1L);
		pn.setNumber("0412341234");
		
		return pn;
	}
	
}
