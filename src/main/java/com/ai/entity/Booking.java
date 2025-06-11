package com.ai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class Booking extends BaseEntity{
	
	@Column(name = "customer_id", nullable = false)
	private UUID customerId;
	
	@Column(name = "customer_name", nullable = false)
	private String customerName;
	
	@Column(name = "customer_surname", nullable = false)
	private String customerSurname;
	
	@Column(name = "booking_date", nullable = false)
	private LocalDate bookingDate;
	
	@Column(name = "booking_time", nullable = false)
	private LocalDateTime bookingTime;
	
	@Column(name = "booking_location", nullable = false)
	private String bookingLocation;
	
	@Column(name = "booking_status", nullable = false)
	private String bookingStatus;
	
	@Column(name = "booking_type", nullable = false)
	private String bookingType;
}
