package createbooking.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CreateBookingInputBoundary {
    String createBooking(String request) throws JsonProcessingException;
}
