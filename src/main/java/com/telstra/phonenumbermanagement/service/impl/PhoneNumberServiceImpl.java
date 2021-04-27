package com.telstra.phonenumbermanagement.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.telstra.phonenumbermanagement.common.Status;
import com.telstra.phonenumbermanagement.entity.PhoneNumber;
import com.telstra.phonenumbermanagement.repository.PhoneNumberRepository;
import com.telstra.phonenumbermanagement.service.PhoneNumberService;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {
	
	@Autowired
	private PhoneNumberRepository phoneNumberRepository; 
	
	@Override
	public Optional<PhoneNumber> findById(Long id) {
		return phoneNumberRepository.findById(id);
	}

	@Override
	public Optional<PhoneNumber> findByNumber(String number) {
		return phoneNumberRepository.findByNumber(number);
	}

	@Override
	public PhoneNumber save(PhoneNumber number) {
		return phoneNumberRepository.save(number);
	}

	@Override
	public Page<PhoneNumber> findAll(int pageNumber, int count) {
		Pageable pageable = PageRequest.of(pageNumber, count, Sort.by("number"));
		return phoneNumberRepository.findAll(pageable); 
	}
	
	@Override
	public Page<PhoneNumber> findAllByStatus(Status status, int pageNumber, int count) {
		Pageable pageable = PageRequest.of(pageNumber, count, Sort.by("number"));
		return  phoneNumberRepository.findAllByStatus(status, pageable); 
	}
}
