package search.repository;

import entity.Booking;
import java.time.LocalDateTime;
import java.util.List;

public interface SearchBookingRepository {
    List<Booking> findByDepartureBefore(LocalDateTime departureTime);
    List<Booking> findByItineraryContainingAirports(String airportFrom, String airportTo);
}