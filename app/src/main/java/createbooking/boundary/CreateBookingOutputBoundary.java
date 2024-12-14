package createbooking.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CreateBookingOutputBoundary {
    void present(String response) throws JsonProcessingException;
}
