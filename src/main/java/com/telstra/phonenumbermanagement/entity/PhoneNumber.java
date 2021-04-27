package com.telstra.phonenumbermanagement.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.telstra.phonenumbermanagement.common.MobilePlan;
import com.telstra.phonenumbermanagement.common.Status;

@Entity
@Table(name = "phone_number", indexes = { @Index(columnList = "number") })
public class PhoneNumber {
	
	@Id
	@GeneratedValue
	private Long id; 
	
	@Column(length = 16, unique = true)
	private String number; 
	
	@Column(length = 16)
	private String imei; 
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Enumerated(EnumType.STRING)
	private MobilePlan plan; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	@JsonBackReference
	private Customer customer;
	
	private LocalDate addedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public MobilePlan getPlan() {
		return plan;
	}

	public void setPlan(MobilePlan plan) {
		this.plan = plan;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LocalDate getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDate addedDate) {
		this.addedDate = addedDate;
	}

	@Override
	public String toString() {
		return "PhoneNumber [id=" + id + ", number=" + number + ", imei=" + imei + ", status=" + status + ", plan="
				+ plan + ", customer=" + customer + ", addedDate=" + addedDate + "]";
	}

}
