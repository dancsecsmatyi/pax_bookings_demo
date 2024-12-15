package rest.controller;

import createbooking.boundary.CreateBookingInputBoundary;
import mapper.BookingRequestResponseObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import search.boundary.SearchBookingsInputBoundary;

import static org.mockito.Mockito.mock;

@Configuration
@EnableWebMvc
public class ControllerTestConfig {
    @Bean
    public BookingRestController bookingRestController(
            BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper) {
        return new BookingRestController(
                mock(CreateBookingInputBoundary.class),
                bookingRequestResponseObjectMapper,
                mock(SearchBookingsInputBoundary.class));
    }

    @Bean
    public BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper() {
        return new BookingRequestResponseObjectMapper();
    }
}
