// app/src/test/java/createbooking/interactor/CreateBookingInteractorTest.java
package usecase.createbooking.interactor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import createbooking.boundary.CreateBookingOutputBoundary;
import createbooking.interactor.CreateBookingInteractor;
import model.request.CreateBookingRequest;
import model.response.CreateBookingResponse;
import createbooking.repository.CreateBookingRepository;
import entity.Booking;
import mapper.BookingRequestResponseObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateBookingInteractorTest {

    @Mock
    private CreateBookingOutputBoundary outputBoundary;

    @Mock
    private CreateBookingRepository createBookingRepository;

    @Mock
    private BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper;

    @InjectMocks
    private CreateBookingInteractor createBookingInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBooking() throws JsonProcessingException {
        // Arrange
        CreateBookingRequest request = new CreateBookingRequest("John Doe", LocalDateTime.now(), List.of("Location1", "Location2"));
        String requestJson = "{\"paxName\":\"John Doe\",\"departure\":\"2023-10-10T10:00:00\",\"itinerary\":[\"Location1\",\"Location2\"]}";
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(bookingRequestResponseObjectMapper.getObjectMapperWithNecessaryModule()).thenReturn(objectMapper);
        when(objectMapper.readValue(requestJson, CreateBookingRequest.class)).thenReturn(request);

        // Act
        createBookingInteractor.createBooking(requestJson);

        // Assert
        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
        verify(createBookingRepository).save(bookingCaptor.capture());
        Booking savedBooking = bookingCaptor.getValue();
        assertThat(savedBooking).extracting(Booking::paxName, Booking::departure, Booking::itinerary)
                .containsExactly(request.paxName(), request.departure(), request.itinerary());

        ArgumentCaptor<String> responseCaptor = ArgumentCaptor.forClass(String.class);
        verify(outputBoundary).present(responseCaptor.capture());
        CreateBookingResponse response = new CreateBookingResponse(savedBooking.uuid(), savedBooking.paxName(), savedBooking.departure(), savedBooking.itinerary());
        when(objectMapper.writeValueAsString(response)).thenReturn(responseCaptor.getValue());
        assertThat(responseCaptor.getValue()).isEqualTo(objectMapper.writeValueAsString(response));
    }
}