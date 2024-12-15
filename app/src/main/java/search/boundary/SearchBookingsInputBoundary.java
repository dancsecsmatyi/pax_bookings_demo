package search.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SearchBookingsInputBoundary {
    String searchBookingsByDeparture(String request) throws JsonProcessingException;
    String searchBookingsVisitingTwoAirports(String request) throws JsonProcessingException;
}