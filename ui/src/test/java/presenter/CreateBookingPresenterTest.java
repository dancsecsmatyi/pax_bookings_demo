// ui/src/test/java/presenter/CreateBookingPresenterTest.java
package presenter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mapper.BookingRequestResponseObjectMapper;
import model.response.CreateBookingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.ConsoleResultView;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

public class CreateBookingPresenterTest {

    @InjectMocks
    private CreateBookingPresenter presenter;
    @Mock
    private BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper;
    @Mock private ConsoleResultView consoleResultView;
    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(bookingRequestResponseObjectMapper.getObjectMapperWithNecessaryModule())
                .thenReturn(objectMapper);
    }

    @Test
    public void testPresent() throws JsonProcessingException {
        // Arrange
        String jsonResponse = "{\"id\":\"565110d1-ed7f-4808-bced-38c1157f6e61\",\"paxName\":\"Test\",\"departure\":\"2024-12-30T12:12:12\",\"itinerary\":[\"AMS\",\"LHR\"]}";
        CreateBookingResponse expectedResponse = new CreateBookingResponse("565110d1-ed7f-4808-bced-38c1157f6e61", "Test", LocalDateTime.of(2024,12,30,12,12,12), List.of("AMS", "LHR"));

        when(objectMapper.readValue(jsonResponse, CreateBookingResponse.class))
                .thenReturn(expectedResponse);

        // Act
        presenter.present(jsonResponse);

        // Assert
        verify(consoleResultView, times(1)).display(expectedResponse);
    }
}