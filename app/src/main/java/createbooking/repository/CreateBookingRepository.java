package createbooking.repository;

import entity.Booking;

public interface CreateBookingRepository {
    void save(Booking booking);
}