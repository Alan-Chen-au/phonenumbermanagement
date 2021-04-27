package com.telstra.phonenumbermanagement.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.telstra.phonenumbermanagement.common.Status;
import com.telstra.phonenumbermanagement.entity.PhoneNumber;
import com.telstra.phonenumbermanagement.repository.PhoneNumberRepository;
import com.telstra.phonenumbermanagement.service.PhoneNumberService;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PhoneNumberServiceImplTest {

	@Mock
	private PhoneNumberRepository phoneNumberRepository; 
	
	@InjectMocks
	private PhoneNumberService phoneNumberService = new PhoneNumberServiceImpl(); 
	
	private String number = "040012341234";
	private Long id = 1L; 
	
	@BeforeEach
	public void setMockOutput() {
		PhoneNumber pn = new PhoneNumber(); 
		pn.setId(id);
		pn.setNumber(number);
		pn.setStatus(Status.PENDING);
		
		List<PhoneNumber> list = new ArrayList<>(); 
		list.add(pn); 
		
		when(phoneNumberService.findById(id)).thenReturn(Optional.of(pn)); 
		when(phoneNumberService.findByNumber(number)).thenReturn(Optional.of(pn)); 
		when(phoneNumberService.save(Mockito.any(PhoneNumber.class))).thenReturn(pn); 
		when(phoneNumberService.findAll(0, 5)).thenReturn(new PageImpl<PhoneNumber>(list)); 
		when(phoneNumberService.findAll(0, 5)).thenReturn(new PageImpl<PhoneNumber>(list)); 
		when(phoneNumberService.findAllByStatus(Status.PENDING, 0, 5)).thenReturn(new PageImpl<PhoneNumber>(list)); 
	}
	
	@Test
	public void findByIdTest() {
		Optional<PhoneNumber> optional = phoneNumberService.findById(id); 
		assertEquals(id, optional.get().getId()); 
		assertEquals(number, optional.get().getNumber()); 
	}
	
	@Test
	public void findByNumberTest() {
		Optional<PhoneNumber> optional = phoneNumberService.findByNumber(number); 
		assertEquals(id, optional.get().getId()); 
		assertEquals(number, optional.get().getNumber()); 
	}
	
	@Test
	public void saveTest() {
		PhoneNumber p = phoneNumberService.save(new PhoneNumber()); 
		assertEquals(id, p.getId()); 
		assertEquals(number, p.getNumber()); 
	}
	
	@Test
	public void findAllTest() {
		Page<PhoneNumber> page = phoneNumberService.findAll(0, 5); 
		assertEquals(1, page.getContent().size()); 
		assertEquals(1, page.getContent().get(0).getId()); 
		assertEquals(number, page.getContent().get(0).getNumber()); 
	}
	
	@Test
	public void findAllByStatusTest() {
		Page<PhoneNumber> page = phoneNumberService.findAllByStatus(Status.PENDING, 0, 5); 
		assertEquals(1, page.getContent().size()); 
		assertEquals(1, page.getContent().get(0).getId()); 
		assertEquals(number, page.getContent().get(0).getNumber()); 
		assertEquals(Status.PENDING, page.getContent().get(0).getStatus()); 
	}
}
