package createbooking.interactor;

import com.fasterxml.jackson.core.JsonProcessingException;
import createbooking.boundary.CreateBookingInputBoundary;
import createbooking.boundary.CreateBookingOutputBoundary;
import model.request.CreateBookingRequest;
import model.response.CreateBookingResponse;
import createbooking.repository.CreateBookingRepository;
import entity.Booking;
import mapper.BookingRequestResponseObjectMapper;

import java.util.UUID;

public class CreateBookingInteractor implements CreateBookingInputBoundary {
    private final CreateBookingOutputBoundary outputBoundary;
    private final CreateBookingRepository createBookingRepository;
    private final BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper;

    public CreateBookingInteractor(
            CreateBookingOutputBoundary outputBoundary,
            CreateBookingRepository createBookingRepository, BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper) {
        this.outputBoundary = outputBoundary;
        this.createBookingRepository = createBookingRepository;
        this.bookingRequestResponseObjectMapper = bookingRequestResponseObjectMapper;
    }

    @Override
    public void createBooking(String request) throws JsonProcessingException {

        String id = UUID.randomUUID().toString();
        CreateBookingRequest createBookingRequest =
                bookingRequestResponseObjectMapper
                        .getObjectMapperWithNecessaryModule()
                        .readValue(request, CreateBookingRequest.class);
        Booking booking =
                new Booking(
                        id,
                        createBookingRequest.paxName(),
                        createBookingRequest.departure(),
                        createBookingRequest.itinerary());

        createBookingRepository.save(booking);

        CreateBookingResponse createBookingResponse =
                new CreateBookingResponse(
                        id, booking.paxName(), booking.departure(), booking.itinerary());

        String response =
                bookingRequestResponseObjectMapper
                        .getObjectMapperWithNecessaryModule()
                        .writeValueAsString(createBookingResponse);

        outputBoundary.present(response);
    }
}
