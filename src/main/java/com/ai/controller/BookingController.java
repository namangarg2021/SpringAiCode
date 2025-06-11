package com.ai.controller;

import com.ai.entity.Booking;
import com.ai.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
	
	@Autowired
	BookingService bookingService;
	
	@PostMapping("/add")
	public ResponseEntity addBooking(@RequestBody List<Booking> bookings) {
		bookingService.addBooking(bookings);
		return ResponseEntity.ok().build();
	}
}