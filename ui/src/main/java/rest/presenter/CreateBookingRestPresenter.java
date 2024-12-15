package rest.presenter;

import com.fasterxml.jackson.core.JsonProcessingException;


import createbooking.boundary.CreateBookingOutputBoundary;

import lombok.extern.slf4j.Slf4j;
import mapper.BookingRequestResponseObjectMapper;

import model.response.CreateBookingResponse;

@Slf4j
public class CreateBookingRestPresenter implements CreateBookingOutputBoundary {

    BookingRequestResponseObjectMapper objectMapper = new BookingRequestResponseObjectMapper();

    @Override
    public void present(String response) throws JsonProcessingException {
        CreateBookingResponse createBookingResponse =
                objectMapper
                        .getObjectMapperWithNecessaryModule()
                        .readValue(response, CreateBookingResponse.class);
        log.info("Created successful! Booking ID: " + createBookingResponse.id());
    }
}
