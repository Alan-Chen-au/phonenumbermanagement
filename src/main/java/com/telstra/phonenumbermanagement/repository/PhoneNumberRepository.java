package com.telstra.phonenumbermanagement.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telstra.phonenumbermanagement.common.Status;
import com.telstra.phonenumbermanagement.entity.PhoneNumber;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
	
	public Optional<PhoneNumber> findByNumber(String number); 
	
	public Page<PhoneNumber> findAll(Pageable pageable); 
	
	public Page<PhoneNumber> findAllByStatus(Status status, Pageable pageable); 
}
