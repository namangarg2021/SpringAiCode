package com.ai.components;

import com.ai.entity.Booking;
import com.ai.service.BookingService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookingTools {
	
	private final BookingService bookingService;
	
	public BookingTools(BookingService bookingService) {
		this.bookingService = bookingService;
	}
	
	@Tool
	public Booking getBookingDetailsByName(String customerName) {
		return bookingService.getBookingDetails(customerName);
	}
	
	@Tool
	public Booking getBookingDetailsByNameAndSurName(String customerName, String customerSurname) {
		return bookingService.getBookingDetails(customerName, customerSurname);
	}
	
	@Tool
	public void cancelBooking(UUID bookingId) {
		bookingService.cancelBooking(bookingId);
	}
}