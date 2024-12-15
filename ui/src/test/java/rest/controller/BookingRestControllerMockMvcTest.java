package rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import createbooking.boundary.CreateBookingInputBoundary;
import mapper.BookingRequestResponseObjectMapper;
import model.request.CreateBookingRequest;
import model.request.SearchBookingsByAirportsRequest;
import model.request.SearchBookingsByDepartureRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import search.boundary.SearchBookingsInputBoundary;

import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ControllerTestConfig.class)
@AutoConfigureMockMvc
public class BookingRestControllerMockMvcTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean private CreateBookingInputBoundary createBookingInputBoundary;

    @MockitoBean private BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper;

    @MockitoBean private ObjectMapper objectMapper;

    @MockitoBean private SearchBookingsInputBoundary searchBookingsInputBoundary;

    private ObjectMapper realObjectMapper;

    @BeforeEach
    public void setUp() {
        realObjectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateBooking() throws Exception {
        CreateBookingRequest request =
                new CreateBookingRequest("Test", "2021-12-12T12:00", List.of("AMS", "LHR"));
        String requestJson = realObjectMapper.writeValueAsString(request);

        when(bookingRequestResponseObjectMapper.getObjectMapperWithNecessaryModule())
                .thenReturn(objectMapper);
        when(objectMapper.writeValueAsString(any(CreateBookingRequest.class)))
                .thenReturn(requestJson);

        mockMvc.perform(
                        post("/bookings/create")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testSearchByDeparturetime() throws Exception {
        SearchBookingsByDepartureRequest request =
                new SearchBookingsByDepartureRequest("2021-12-12T12:00");
        String requestJson = realObjectMapper.writeValueAsString(request);

        when(bookingRequestResponseObjectMapper.getObjectMapperWithNecessaryModule())
                .thenReturn(realObjectMapper);

        mockMvc.perform(
                        post("/bookings/searchByDeparturetime")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchByAirports() throws Exception {
        SearchBookingsByAirportsRequest request = new SearchBookingsByAirportsRequest("AMS", "LHR");
        String requestJson = realObjectMapper.writeValueAsString(request);

        when(bookingRequestResponseObjectMapper.getObjectMapperWithNecessaryModule())
                .thenReturn(realObjectMapper);
        when(bookingRequestResponseObjectMapper.getObjectMapperWithNecessaryModule())
                .thenReturn(realObjectMapper);

        mockMvc.perform(
                        post("/bookings/searchByAirports")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateBookingWithEmptyRequest() throws Exception {
        CreateBookingRequest request = new CreateBookingRequest();
        String requestJson = realObjectMapper.writeValueAsString(request);

        mockMvc.perform(
                        post("/bookings/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchByDeparturetimeWithEmptyRequest() throws Exception {
        SearchBookingsByDepartureRequest request = new SearchBookingsByDepartureRequest();
        String requestJson = realObjectMapper.writeValueAsString(request);

        mockMvc.perform(
                        post("/bookings/searchByDeparturetime")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchByAirportsWithEmptyRequest() throws Exception {
        SearchBookingsByAirportsRequest request = new SearchBookingsByAirportsRequest();
        String requestJson = realObjectMapper.writeValueAsString(request);

        mockMvc.perform(
                        post("/bookings/searchByAirports")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(status().isBadRequest());
    }
}
