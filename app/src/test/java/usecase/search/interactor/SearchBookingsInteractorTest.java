package usecase.search.interactor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Booking;
import mapper.BookingRequestResponseObjectMapper;
import model.response.SearchBookingsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import search.boundary.SearchBookingsOutputBoundary;
import search.interactor.SearchBookingsInteractor;
import model.request.SearchBookingsByAirportsRequest;
import model.request.SearchBookingsByDepartureRequest;
import search.repository.SearchBookingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SearchBookingsInteractorTest {

    @Mock
    private SearchBookingRepository searchBookingRepository;

    @Mock
    private SearchBookingsOutputBoundary outputBoundary;

    @Mock
    private BookingRequestResponseObjectMapper objectMapper;

    @InjectMocks
    private SearchBookingsInteractor searchBookingsInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchBookingsByDeparture() throws JsonProcessingException {

        String requestJson = "{\"departureTime\":\"2023-10-10T10:00\"}";
        SearchBookingsByDepartureRequest request = new SearchBookingsByDepartureRequest(LocalDateTime.of
                (2023, 10, 10, 10, 0).toString());
        ObjectMapper objectMapperWithNecessaryModule = mock(ObjectMapper.class);
        when(objectMapper.getObjectMapperWithNecessaryModule()).thenReturn(objectMapperWithNecessaryModule);
        when(objectMapperWithNecessaryModule.readValue(requestJson, SearchBookingsByDepartureRequest.class)).thenReturn(request);
        when(objectMapperWithNecessaryModule.writeValueAsString(any(SearchBookingsResponse.class))).thenReturn("json");
        when(searchBookingRepository.findByDepartureBefore(any(LocalDateTime.class)))
                .thenReturn(
                        List.of(
                                new Booking(
                                        UUID.randomUUID().toString(),
                                        "John Doe",
                                        LocalDateTime.now(),
                                        List.of("AirportFrom", "AirportTo"))));

        assertDoesNotThrow(() -> searchBookingsInteractor.searchBookingsByDeparture(requestJson));
        verify(outputBoundary, times(1)).present(any(String.class));
    }

    @Test
    void testSearchBookingsByDepartureVisitingTwoAirports() throws JsonProcessingException {

        String requestJson = "{\"airportFirst\":\"AirportFrom\", \"airportSecond\":\"AirportTo\"}";
        SearchBookingsByAirportsRequest request = new SearchBookingsByAirportsRequest("AirportFrom", "AirportTo");
        ObjectMapper objectMapperWithNecessaryModule = getObjectMapperWithMockedMethods(requestJson, request);
        when(objectMapperWithNecessaryModule.writeValueAsString(any(SearchBookingsResponse.class))).thenReturn("json");
        when(searchBookingRepository.findByItineraryContainingAirports(anyString(), anyString()))
                .thenReturn(
                        List.of(
                                new Booking(
                                        UUID.randomUUID().toString(),
                                        "John Doe",
                                        LocalDateTime.now(),
                                        List.of("AirportFrom", "AirportTo"))));

        assertDoesNotThrow(() -> searchBookingsInteractor.searchBookingsVisitingTwoAirports(requestJson));
        verify(outputBoundary, times(1)).present(any(String.class));
    }

    @Test
    void testSearchBookingsByDepartureVisitingTwoAirportsReverseDirection() throws JsonProcessingException {

        String requestJson = "{\"airportFirst\":\"AirportFrom\", \"airportSecond\":\"AirportTo\"}";
        SearchBookingsByAirportsRequest request = new SearchBookingsByAirportsRequest("AirportFrom", "AirportTo");
        getObjectMapperWithMockedMethods(requestJson, request);
        when(searchBookingRepository.findByItineraryContainingAirports(anyString(), anyString()))
                .thenReturn(
                        List.of(
                                new Booking(
                                        UUID.randomUUID().toString(),
                                        "John Doe",
                                        LocalDateTime.now(),
                                        List.of("AirportTo", "AirportFrom"))));

        assertDoesNotThrow(() -> searchBookingsInteractor.searchBookingsVisitingTwoAirports(requestJson));
        verify(outputBoundary, times(0)).present(any(String.class));
    }

    @Test
    void testSearchBookingsByDepartureWithInvalidJson() throws JsonProcessingException {
        String invalidJson = "invalid json";
        ObjectMapper objectMapperWithNecessaryModule = mock(ObjectMapper.class);
        when(objectMapper.getObjectMapperWithNecessaryModule()).thenReturn(objectMapperWithNecessaryModule);
        when(objectMapperWithNecessaryModule.readValue(invalidJson, SearchBookingsByDepartureRequest.class)).thenThrow(JsonProcessingException.class);

        assertDoesNotThrow(() -> searchBookingsInteractor.searchBookingsByDeparture(invalidJson));
        verify(outputBoundary, times(0)).present(any(String.class));
    }

    @Test
    void testSearchBookingsByDepartureWithEmptyResult() throws JsonProcessingException {
        String requestJson = "{\"departureTime\":\"2023-10-10T10:00\"}";
        SearchBookingsByDepartureRequest request = new SearchBookingsByDepartureRequest(LocalDateTime.of(2023, 10, 10, 10, 0).toString());
        ObjectMapper objectMapperWithNecessaryModule = mock(ObjectMapper.class);
        when(objectMapper.getObjectMapperWithNecessaryModule()).thenReturn(objectMapperWithNecessaryModule);
        when(objectMapperWithNecessaryModule.readValue(requestJson, SearchBookingsByDepartureRequest.class)).thenReturn(request);
        when(objectMapperWithNecessaryModule.writeValueAsString(any(SearchBookingsResponse.class))).thenReturn("json");
        when(searchBookingRepository.findByDepartureBefore(any(LocalDateTime.class))).thenReturn(List.of());

        assertDoesNotThrow(() -> searchBookingsInteractor.searchBookingsByDeparture(requestJson));
        verify(outputBoundary, times(1)).present(any(String.class));
    }

    @Test
    void testSearchBookingsByAirportsWithInvalidJson() throws JsonProcessingException {
        String invalidJson = "invalid json";
        ObjectMapper objectMapperWithNecessaryModule = mock(ObjectMapper.class);
        when(objectMapper.getObjectMapperWithNecessaryModule()).thenReturn(objectMapperWithNecessaryModule);
        when(objectMapperWithNecessaryModule.readValue(invalidJson, SearchBookingsByDepartureRequest.class)).thenThrow(JsonProcessingException.class);

        assertDoesNotThrow(() -> searchBookingsInteractor.searchBookingsVisitingTwoAirports(invalidJson));
        verify(outputBoundary, times(0)).present(any(String.class));
    }

    @Test
    void testSearchBookingsByAirportsWithEmptyResult() throws JsonProcessingException {
        String requestJson = "{\"airportFirst\":\"N/A\", \"airportSecond\":\"N/A\"}";
        SearchBookingsByAirportsRequest request = new SearchBookingsByAirportsRequest("AirportFrom", "AirportTo");
        ObjectMapper objectMapperWithNecessaryModule = mock(ObjectMapper.class);
        when(objectMapper.getObjectMapperWithNecessaryModule()).thenReturn(objectMapperWithNecessaryModule);
        when(objectMapperWithNecessaryModule.readValue(requestJson, SearchBookingsByAirportsRequest.class)).thenReturn(request);
        when(objectMapperWithNecessaryModule.writeValueAsString(any(SearchBookingsResponse.class))).thenReturn("json");
        when(searchBookingRepository.findByDepartureBefore(any(LocalDateTime.class))).thenReturn(List.of());

        assertDoesNotThrow(() -> searchBookingsInteractor.searchBookingsVisitingTwoAirports(requestJson));
        verify(outputBoundary, times(1)).present(any(String.class));
    }

    private ObjectMapper getObjectMapperWithMockedMethods(String requestJson, SearchBookingsByAirportsRequest request) throws JsonProcessingException {
        ObjectMapper objectMapperWithNecessaryModule = mock(ObjectMapper.class);
        when(objectMapper.getObjectMapperWithNecessaryModule()).thenReturn(objectMapperWithNecessaryModule);
        when(objectMapperWithNecessaryModule.readValue(requestJson, SearchBookingsByAirportsRequest.class)).thenReturn(request);
        return objectMapperWithNecessaryModule;
    }
}