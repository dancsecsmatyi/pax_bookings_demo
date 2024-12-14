// src/test/java/presenter/SearchBookingsPresenterTest.java
package presenter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mapper.BookingRequestResponseObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import model.response.SearchBookingsResponse;
import model.dto.SearchBookingDto;
import org.mockito.MockitoAnnotations;
import view.ConsoleResultView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchBookingsPresenterTest {

    @InjectMocks private SearchBookingsPresenter presenter;
    @Mock private BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper;
    @Mock private ConsoleResultView consoleResultView;
    @Mock private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(bookingRequestResponseObjectMapper.getObjectMapperWithNecessaryModule())
                .thenReturn(objectMapper);
    }

    @Test
    public void testPresent() throws JsonProcessingException {
        SearchBookingsResponse response =
                new SearchBookingsResponse(
                        Collections.singletonList(
                                new SearchBookingDto(
                                        "John Doe",
                                        LocalDateTime.of(
                                                LocalDate.of(2023, 10, 1), LocalTime.of(12, 0, 0)),
                                        Collections.singletonList("NYC"))));
        String responseJson =
                "{\"bookings\":[{\"paxName\":\"John Doe\",\"departure\":\"2023-10-01 12:00:00\",\"itinerary\":[\"NYC\"]}]}";

        when(objectMapper.readValue(responseJson, SearchBookingsResponse.class))
                .thenReturn(response);

        presenter.present(responseJson);

        verify(consoleResultView, times(1)).display(response);
    }
}
