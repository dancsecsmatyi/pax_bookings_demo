package createbooking.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CreateBookingInputBoundary {
    void createBooking(String request) throws JsonProcessingException;
}
