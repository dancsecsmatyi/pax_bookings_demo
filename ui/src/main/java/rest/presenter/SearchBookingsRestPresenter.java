package rest.presenter;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import mapper.BookingRequestResponseObjectMapper;

import model.response.SearchBookingsResponse;

import search.boundary.SearchBookingsOutputBoundary;

@Slf4j
public class SearchBookingsRestPresenter implements SearchBookingsOutputBoundary {

    private final BookingRequestResponseObjectMapper objectMapper;

    public SearchBookingsRestPresenter(BookingRequestResponseObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void present(String response) throws JsonProcessingException {
        SearchBookingsResponse searchBookingsResponse =
                objectMapper.getObjectMapperWithNecessaryModule().readValue(response, SearchBookingsResponse.class);
        log.info("SearchBookingsResponse: {}", searchBookingsResponse);
    }
}
