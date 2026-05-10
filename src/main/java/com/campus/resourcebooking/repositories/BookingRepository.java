package com.campus.resourcebooking.repositories;

import com.campus.resourcebooking.model.Booking;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends Repository<Booking, String> {
    List<Booking> findByUserId(String userId);
    List<Booking> findByResourceId(String resourceId);
    Optional<Booking> findByBookingId(String bookingId);
}
