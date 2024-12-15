package createbooking.interactor;

import com.fasterxml.jackson.core.JsonProcessingException;
import constant.CommonConstants;
import createbooking.boundary.CreateBookingInputBoundary;
import createbooking.boundary.CreateBookingOutputBoundary;
import model.request.CreateBookingRequest;
import model.response.CreateBookingResponse;
import createbooking.repository.CreateBookingRepository;
import entity.Booking;
import mapper.BookingRequestResponseObjectMapper;
import model.response.Result;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateBookingInteractor implements CreateBookingInputBoundary {
    private final CreateBookingOutputBoundary outputBoundary;
    private final CreateBookingRepository createBookingRepository;
    private final BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper;

    public CreateBookingInteractor(
            CreateBookingOutputBoundary outputBoundary,
            CreateBookingRepository createBookingRepository,
            BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper) {
        this.outputBoundary = outputBoundary;
        this.createBookingRepository = createBookingRepository;
        this.bookingRequestResponseObjectMapper = bookingRequestResponseObjectMapper;
    }

    @Override
    public String createBooking(String request) throws JsonProcessingException {
        CreateBookingRequest createBookingRequest = getCreateBookingRequest(request);
        Booking booking = initBookingWithIdGenerate(createBookingRequest);
        Result result = saveBookingWithGetResult(booking);
        CreateBookingResponse createBookingResponse = createBookingResponse(booking, result);
        String response = getResponseJson(createBookingResponse);
        outputBoundary.present(response);
        return response;
    }

    private Result saveBookingWithGetResult(Booking booking) {
        Result result = Result.SUCCESS;
        try {
            createBookingRepository.save(booking);
        } catch (Exception e) {
            result = Result.FAILED;
        }
        return result;
    }

    private Booking initBookingWithIdGenerate(CreateBookingRequest createBookingRequest) {
        String id = UUID.randomUUID().toString();
        return new Booking(
                id,
                createBookingRequest.getPaxName(),
                parseDepartureTime(createBookingRequest.getDeparture()),
                createBookingRequest.getItinerary());
    }

    private CreateBookingRequest getCreateBookingRequest(String request)
            throws JsonProcessingException {
        return bookingRequestResponseObjectMapper
                .getObjectMapperWithNecessaryModule()
                .readValue(request, CreateBookingRequest.class);
    }

    private CreateBookingResponse createBookingResponse(Booking booking, Result result) {
        return new CreateBookingResponse(
                result,
                booking.getUuid(),
                booking.getPaxName(),
                booking.getDeparture(),
                booking.getItinerary());
    }

    private String getResponseJson(CreateBookingResponse createBookingResponse)
            throws JsonProcessingException {
        return bookingRequestResponseObjectMapper
                .getObjectMapperWithNecessaryModule()
                .writeValueAsString(createBookingResponse);
    }

    private LocalDateTime parseDepartureTime(String departureTime) {
        return LocalDateTime.parse(departureTime, CommonConstants.DATE_TIME_FORMATTER);
    }
}
