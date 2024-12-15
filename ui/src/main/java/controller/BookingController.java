package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import model.dto.CreateBookingDto;
import createbooking.boundary.CreateBookingInputBoundary;
import model.request.CreateBookingRequest;
import mapper.BookingRequestResponseObjectMapper;
import search.boundary.SearchBookingsInputBoundary;
import model.request.SearchBookingsByAirportsRequest;
import model.request.SearchBookingsByDepartureRequest;

import java.time.LocalDateTime;

public class BookingController {
    private final CreateBookingInputBoundary createBookingInputBoundary;
    private final SearchBookingsInputBoundary searchBookingsInputBoundary;
    private final BookingRequestResponseObjectMapper objectMapper;

    public BookingController(
            CreateBookingInputBoundary createBookingInputBoundary,
            SearchBookingsInputBoundary searchBookingsBoundary,
            BookingRequestResponseObjectMapper objectMapper) {
        this.createBookingInputBoundary = createBookingInputBoundary;
        this.searchBookingsInputBoundary = searchBookingsBoundary;
        this.objectMapper = objectMapper;
    }

    public void createBooking(@Valid CreateBookingDto createBookingDto) throws JsonProcessingException {
        CreateBookingRequest request =
                new CreateBookingRequest(
                        createBookingDto.getPaxName(),
                        createBookingDto.getDeparture().toString(),
                        createBookingDto.getItinerary());
        createBookingInputBoundary.createBooking(
                objectMapper.getObjectMapperWithNecessaryModule().writeValueAsString(request));
    }

    public void searchBookingsByDeparture(LocalDateTime departureTime) throws JsonProcessingException {
        SearchBookingsByDepartureRequest request =
                new SearchBookingsByDepartureRequest(departureTime.toString());
        searchBookingsInputBoundary.searchBookingsByDeparture(
                objectMapper.getObjectMapperWithNecessaryModule().writeValueAsString(request));
    }

    public void searchBookingsByAirports(String airportFrom, String airportTo)
            throws JsonProcessingException {
        SearchBookingsByAirportsRequest request =
                new SearchBookingsByAirportsRequest(airportFrom, airportTo);
        searchBookingsInputBoundary.searchBookingsVisitingTwoAirports(
                objectMapper.getObjectMapperWithNecessaryModule().writeValueAsString(request));
    }
}
