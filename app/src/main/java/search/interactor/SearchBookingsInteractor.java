package search.interactor;

import com.fasterxml.jackson.core.JsonProcessingException;
import entity.Booking;
import lombok.extern.slf4j.Slf4j;
import mapper.BookingRequestResponseObjectMapper;
import model.response.Result;
import model.response.SearchBookingsResponse;
import search.boundary.SearchBookingsInputBoundary;
import search.boundary.SearchBookingsOutputBoundary;
import model.request.SearchBookingsByAirportsRequest;
import model.request.SearchBookingsByDepartureRequest;
import model.dto.SearchBookingDto;
import search.repository.SearchBookingRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SearchBookingsInteractor implements SearchBookingsInputBoundary {
    private final SearchBookingRepository searchBookingRepository;
    private final SearchBookingsOutputBoundary outputBoundary;
    private final BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper;

    public SearchBookingsInteractor(
            SearchBookingsOutputBoundary outputBoundary,
            SearchBookingRepository searchBookingRepository,
            BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper) {
        this.searchBookingRepository = searchBookingRepository;
        this.outputBoundary = outputBoundary;
        this.bookingRequestResponseObjectMapper = bookingRequestResponseObjectMapper;
    }

    @Override
    public String searchBookingsByDeparture(String request) throws JsonProcessingException {
        logSearchBookingStart(request);
        SearchBookingsResponse searchBookingsResponse =
                getSearchBookingsResponseByDeparture(request);
        String responseJson = getResponseJson(searchBookingsResponse);
        outputBoundary.present(responseJson);
        logSearchBookingEnd(responseJson);
        return responseJson;
    }

    @Override
    public String searchBookingsVisitingTwoAirports(String request) throws JsonProcessingException {
        logSearchBookingStart(request);
        SearchBookingsResponse searchBookingsResponse =
                getSearchBookingsResponseByAirports(request);
        String responseJson = getResponseJson(searchBookingsResponse);
        outputBoundary.present(responseJson);
        logSearchBookingEnd(responseJson);
        return responseJson;
    }

    private void logSearchBookingEnd(String responseJson) {
        log.info("Search bookings response: {}", responseJson);
    }

    private void logSearchBookingStart(String request) {
        log.info("Search bookings request: {}", request);
    }

    private SearchBookingsResponse getSearchBookingsResponseByAirports(String request) {
        SearchBookingsByAirportsRequest searchBookingsByAirportsRequest;
        List<SearchBookingDto> bookings = new ArrayList<>();
        Result result = Result.SUCCESS;
        try {
            searchBookingsByAirportsRequest = getSearchBookingsByAirportsRequest(request);
            bookings =
                    searchBookingRepository
                            .findByItineraryContainingAirports(
                                    searchBookingsByAirportsRequest.getAirportFirst(),
                                    searchBookingsByAirportsRequest.getAirportSecond())
                            .stream()
                            .map(this::createSearchBookingDto)
                            .toList();
        } catch (Exception e) {
            result = Result.FAILED;
        }
        return new SearchBookingsResponse(result, bookings);
    }

    private SearchBookingsResponse getSearchBookingsResponseByDeparture(String request) {
        SearchBookingsByDepartureRequest searchBookingsByDepartureRequest;
        List<SearchBookingDto> bookings = new ArrayList<>();
        Result result = Result.SUCCESS;
        try {
            searchBookingsByDepartureRequest = getSearchBookingsByDepartureRequest(request);
            LocalDateTime departureTime =
                    LocalDateTime.parse(searchBookingsByDepartureRequest.getDepartureTime());
            bookings =
                    searchBookingRepository.findByDepartureBefore(departureTime).stream()
                            .map(this::createSearchBookingDto)
                            .toList();
        } catch (Exception e) {
            result = Result.FAILED;
        }
        return new SearchBookingsResponse(result, bookings);
    }

    private SearchBookingDto createSearchBookingDto(Booking booking) {
        return new SearchBookingDto(
                booking.getPaxName(), booking.getDeparture(), booking.getItinerary());
    }

    private SearchBookingsByAirportsRequest getSearchBookingsByAirportsRequest(String request)
            throws JsonProcessingException {
        return bookingRequestResponseObjectMapper
                .getObjectMapperWithNecessaryModule()
                .readValue(request, SearchBookingsByAirportsRequest.class);
    }

    private SearchBookingsByDepartureRequest getSearchBookingsByDepartureRequest(String request)
            throws JsonProcessingException {
        return bookingRequestResponseObjectMapper
                .getObjectMapperWithNecessaryModule()
                .readValue(request, SearchBookingsByDepartureRequest.class);
    }

    private String getResponseJson(SearchBookingsResponse searchBookingsResponse)
            throws JsonProcessingException {
        return bookingRequestResponseObjectMapper
                .getObjectMapperWithNecessaryModule()
                .writeValueAsString(searchBookingsResponse);
    }
}
