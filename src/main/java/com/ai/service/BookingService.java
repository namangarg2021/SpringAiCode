package com.ai.service;

import com.ai.entity.Booking;
import com.ai.repo.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
	
	@Autowired
	BookingRepository bookingRepository;
	
	public Booking getBookingDetails(String customerName) {
		return bookingRepository.findByCustomerName(customerName)
				.orElse(null);
	}
	
	public Booking getBookingDetails(String customerName, String customerSurname) {
		return bookingRepository.findByCustomerNameAndCustomerSurname(customerName,customerSurname)
				.orElse(null);
	}
	
	public void cancelBooking(UUID bookingId) {
		bookingRepository.deleteById(bookingId);
	}
	
	@Transactional
	public void addBooking(List<Booking> booking) {
		bookingRepository.saveAll(booking);
	}
}
