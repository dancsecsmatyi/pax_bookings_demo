package rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import createbooking.boundary.CreateBookingInputBoundary;

import jakarta.validation.Valid;

import mapper.BookingRequestResponseObjectMapper;

import model.request.CreateBookingRequest;
import model.request.SearchBookingsByAirportsRequest;
import model.request.SearchBookingsByDepartureRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import search.boundary.SearchBookingsInputBoundary;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
public class BookingRestController {
    private final CreateBookingInputBoundary createBookingInputBoundary;
    private final BookingRequestResponseObjectMapper objectMapper;
    private final SearchBookingsInputBoundary searchBookingsInputBoundary;

    public BookingRestController(
            CreateBookingInputBoundary createBookingInputBoundary,
            BookingRequestResponseObjectMapper objectMapper,
            SearchBookingsInputBoundary searchBookingsInputBoundary) {
        this.createBookingInputBoundary = createBookingInputBoundary;
        this.objectMapper = objectMapper;
        this.searchBookingsInputBoundary = searchBookingsInputBoundary;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@Valid @RequestBody CreateBookingRequest createBookingRequest) throws JsonProcessingException {
        String responseJson = createBookingInputBoundary.createBooking(
                objectMapper.getObjectMapperWithNecessaryModule().writeValueAsString(createBookingRequest));
        return new ResponseEntity<>(responseJson, HttpStatus.CREATED);
    }

    @PostMapping("/searchByDeparturetime")
    public ResponseEntity<?> searchByDeparturetime(@Valid @RequestBody SearchBookingsByDepartureRequest searchBookingsByDepartureRequest) throws JsonProcessingException {
        String responseJson = searchBookingsInputBoundary.searchBookingsByDeparture(
                objectMapper.getObjectMapperWithNecessaryModule().writeValueAsString(searchBookingsByDepartureRequest));
        return new ResponseEntity<>(responseJson, HttpStatus.OK);
    }

    @PostMapping("/searchByAirports")
    public ResponseEntity<?> searchByAirports(@Valid @RequestBody SearchBookingsByAirportsRequest searchBookingsByAirportsRequest) throws JsonProcessingException {
        String responseJson = searchBookingsInputBoundary.searchBookingsVisitingTwoAirports(
                objectMapper.getObjectMapperWithNecessaryModule().writeValueAsString(searchBookingsByAirportsRequest));
        return new ResponseEntity<>(responseJson, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}