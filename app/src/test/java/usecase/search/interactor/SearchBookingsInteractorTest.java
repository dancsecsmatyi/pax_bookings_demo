// app/src/test/java/search/interactor/SearchBookingsInteractorTest.java
package usecase.search.interactor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Booking;
import mapper.BookingRequestResponseObjectMapper;
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
import java.util.Map;
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
    void testSearchBookings() throws JsonProcessingException {
        // Arrange
        String requestJson = "{\"departureTime\":\"2023-10-10T10:00:00\"}";
        SearchBookingsByDepartureRequest request = new SearchBookingsByDepartureRequest(LocalDateTime.of(2023, 10, 10, 10, 0));
        ObjectMapper objectMapperWithNecessaryModule = mock(ObjectMapper.class);
        when(objectMapper.getObjectMapperWithNecessaryModule()).thenReturn(objectMapperWithNecessaryModule);
        when(objectMapperWithNecessaryModule.readValue(requestJson, SearchBookingsByDepartureRequest.class)).thenReturn(request);
        when(objectMapperWithNecessaryModule.writeValueAsString(any(Map.class))).thenReturn("json");
        when(searchBookingRepository.findByDepartureBefore(any(LocalDateTime.class)))
                .thenReturn(
                        List.of(
                                new Booking(
                                        UUID.randomUUID().toString(),
                                        "John Doe",
                                        LocalDateTime.now(),
                                        List.of("AirportFrom", "AirportTo"))));

        // Act & Assert
        assertDoesNotThrow(() -> searchBookingsInteractor.searchBookings(requestJson));
        verify(outputBoundary, times(1)).present(any(String.class));
    }

    @Test
    void testSearchBookingsVisitingTwoAirports() throws JsonProcessingException {
        // Arrange
        String requestJson = "{\"airportFirst\":\"AirportFrom\", \"airportSecond\":\"AirportTo\"}";
        SearchBookingsByAirportsRequest request = new SearchBookingsByAirportsRequest("AirportFrom", "AirportTo");
        ObjectMapper objectMapperWithNecessaryModule = getObjectMapperWithMockedMethods(requestJson, request);
        when(objectMapperWithNecessaryModule.writeValueAsString(any(Map.class))).thenReturn("json");
        when(searchBookingRepository.findByItineraryContainingAirports(anyString(), anyString()))
                .thenReturn(
                        List.of(
                                new Booking(
                                        UUID.randomUUID().toString(),
                                        "John Doe",
                                        LocalDateTime.now(),
                                        List.of("AirportFrom", "AirportTo"))));

        // Act & Assert
        assertDoesNotThrow(() -> searchBookingsInteractor.searchBookingsVisitingTwoAirports(requestJson));
        verify(outputBoundary, times(1)).present(any(String.class));
    }

    @Test
    void testSearchBookingsVisitingTwoAirportsReverseDirection() throws JsonProcessingException {
        // Arrange
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

        // Act & Assert
        assertDoesNotThrow(() -> searchBookingsInteractor.searchBookingsVisitingTwoAirports(requestJson));
        verify(outputBoundary, times(0)).present(any(String.class));
    }

    private ObjectMapper getObjectMapperWithMockedMethods(String requestJson, SearchBookingsByAirportsRequest request) throws JsonProcessingException {
        ObjectMapper objectMapperWithNecessaryModule = mock(ObjectMapper.class);
        when(objectMapper.getObjectMapperWithNecessaryModule()).thenReturn(objectMapperWithNecessaryModule);
        when(objectMapperWithNecessaryModule.readValue(requestJson, SearchBookingsByAirportsRequest.class)).thenReturn(request);
        return objectMapperWithNecessaryModule;
    }
}