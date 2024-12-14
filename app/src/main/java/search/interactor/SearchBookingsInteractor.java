package search.interactor;

import com.fasterxml.jackson.core.JsonProcessingException;
import mapper.BookingRequestResponseObjectMapper;
import search.boundary.SearchBookingsInputBoundary;
import search.boundary.SearchBookingsOutputBoundary;
import model.request.SearchBookingsByAirportsRequest;
import model.request.SearchBookingsByDepartureRequest;
import model.dto.SearchBookingDto;
import search.repository.SearchBookingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SearchBookingsInteractor implements SearchBookingsInputBoundary {
    private final SearchBookingRepository searchBookingRepository;
    private final SearchBookingsOutputBoundary outputBoundary;
    private final BookingRequestResponseObjectMapper objectMapper;

    public SearchBookingsInteractor(
            SearchBookingsOutputBoundary outputBoundary,
            SearchBookingRepository searchBookingRepository,
            BookingRequestResponseObjectMapper objectMapper) {
        this.searchBookingRepository = searchBookingRepository;
        this.outputBoundary = outputBoundary;
        this.objectMapper = objectMapper;
    }

    @Override
    public void searchBookings(String request) throws JsonProcessingException {
        SearchBookingsByDepartureRequest searchBookingsByDepartureRequest =
                objectMapper.getObjectMapperWithNecessaryModule().readValue(request, SearchBookingsByDepartureRequest.class);
        LocalDateTime departureTime = searchBookingsByDepartureRequest.departureTime();

        List<SearchBookingDto> bookings =
                searchBookingRepository.findByDepartureBefore(departureTime).stream()
                        .map(
                                booking ->
                                        new SearchBookingDto(
                                                booking.paxName(),
                                                booking.departure(),
                                                booking.itinerary()))
                        .toList();
        outputBoundary.present(objectMapper.getObjectMapperWithNecessaryModule().writeValueAsString(Map.of("bookings", bookings)));
    }

    @Override
    public void searchBookingsVisitingTwoAirports(String request) throws JsonProcessingException {
        SearchBookingsByAirportsRequest searchBookingsByAirportsRequest =
                objectMapper.getObjectMapperWithNecessaryModule().readValue(request, SearchBookingsByAirportsRequest.class);
        List<SearchBookingDto> bookings =
                searchBookingRepository
                        .findByItineraryContainingAirports(
                                searchBookingsByAirportsRequest.airportFirst(),
                                searchBookingsByAirportsRequest.airportSecond())
                        .stream()
                        .map(
                                booking ->
                                        new SearchBookingDto(
                                                booking.paxName(),
                                                booking.departure(),
                                                booking.itinerary()))
                        .toList();
        outputBoundary.present(objectMapper.getObjectMapperWithNecessaryModule().writeValueAsString(Map.of("bookings", bookings)));
    }
}
