package com.ai.repo;

import com.ai.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
	
	Optional<Booking> findByCustomerNameAndCustomerSurname(String customerName, String customerSurname);
	
	Optional<Booking> findByCustomerName(String customerName);
}
