package rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import createbooking.boundary.CreateBookingInputBoundary;
import mapper.BookingRequestResponseObjectMapper;
import model.request.CreateBookingRequest;
import model.request.SearchBookingsByAirportsRequest;
import model.request.SearchBookingsByDepartureRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import search.boundary.SearchBookingsInputBoundary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class BookingRestControllerTest {

    @Mock
    private CreateBookingInputBoundary createBookingInputBoundary;

    @Mock
    private BookingRequestResponseObjectMapper objectMapper;

    @Mock
    private SearchBookingsInputBoundary searchBookingsInputBoundary;

    @InjectMocks
    private BookingRestController bookingRestController;

    private ObjectMapper realObjectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        realObjectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateBooking() throws JsonProcessingException {
        CreateBookingRequest request = new CreateBookingRequest();
        String responseJson = "{\"status\":\"created\"}";

        when(objectMapper.getObjectMapperWithNecessaryModule()).thenReturn(realObjectMapper);
        when(createBookingInputBoundary.createBooking(anyString())).thenReturn(responseJson);

        ResponseEntity<?> response = bookingRestController.createBooking(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseJson, response.getBody());
    }

    @Test
    public void testSearchByDeparturetime() throws JsonProcessingException {
        SearchBookingsByDepartureRequest request = new SearchBookingsByDepartureRequest();
        String responseJson = "{\"status\":\"found\"}";

        when(objectMapper.getObjectMapperWithNecessaryModule()).thenReturn(realObjectMapper);
        when(searchBookingsInputBoundary.searchBookingsByDeparture(anyString())).thenReturn(responseJson);

        ResponseEntity<?> response = bookingRestController.searchByDeparturetime(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseJson, response.getBody());
    }

    @Test
    public void testSearchByAirports() throws JsonProcessingException {
        SearchBookingsByAirportsRequest request = new SearchBookingsByAirportsRequest();
        String responseJson = "{\"status\":\"found\"}";

        when(objectMapper.getObjectMapperWithNecessaryModule()).thenReturn(realObjectMapper);
        when(searchBookingsInputBoundary.searchBookingsVisitingTwoAirports(anyString())).thenReturn(responseJson);

        ResponseEntity<?> response = bookingRestController.searchByAirports(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseJson, response.getBody());
    }
}