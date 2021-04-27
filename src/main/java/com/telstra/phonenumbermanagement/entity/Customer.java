package com.telstra.phonenumbermanagement.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.telstra.phonenumbermanagement.common.CustomerType;

@Entity
@Table(name = "customer", indexes = { @Index(columnList = "email") })
public class Customer {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name; 
	
	@Column(name = "email", unique = true)
	private String email; 
	
	private String address; 
	
	private String suburb;
	
	private String state; 
	
	private String postcode; 
	
	private CustomerType type;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = PhoneNumber.class, mappedBy="customer")
	@JsonManagedReference
	private Set<PhoneNumber> numbers = new HashSet<>();
	
	private LocalDate addedDate; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public CustomerType getType() {
		return type;
	}

	public void setType(CustomerType type) {
		this.type = type;
	}

	public LocalDate getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDate addedDate) {
		this.addedDate = addedDate;
	}

	public Set<PhoneNumber> getNumbers() {
		return numbers;
	}

	public void setNumbers(Set<PhoneNumber> numbers) {
		this.numbers = numbers;
	}

	public void addPhoneNumber(PhoneNumber number) {
		numbers.add(number); 
		number.setCustomer(this);
	}
	
	public void removePhoneNumber(PhoneNumber number) {
		numbers.remove(number); 
		number.setCustomer(null);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + ", suburb="
				+ suburb + ", state=" + state + ", postcode=" + postcode + ", type=" + type + ", numbers=" + numbers
				+ ", addedDate=" + addedDate + "]";
	}
}
