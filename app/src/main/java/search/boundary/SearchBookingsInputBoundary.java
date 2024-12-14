package search.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SearchBookingsInputBoundary {
    void searchBookings(String request) throws JsonProcessingException;
    void searchBookingsVisitingTwoAirports(String request) throws JsonProcessingException;
}