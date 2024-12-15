package console.presenter;

import com.fasterxml.jackson.core.JsonProcessingException;
import mapper.BookingRequestResponseObjectMapper;
import search.boundary.SearchBookingsOutputBoundary;
import model.response.SearchBookingsResponse;
import console.view.ConsoleResultView;

public class SearchBookingsPresenter implements SearchBookingsOutputBoundary {

    private final BookingRequestResponseObjectMapper objectMapper;
    private final ConsoleResultView consoleResultView;

    public SearchBookingsPresenter(BookingRequestResponseObjectMapper objectMapper, ConsoleResultView consoleResultView) {
        this.objectMapper = objectMapper;
        this.consoleResultView = consoleResultView;
    }

    @Override
    public void present(String response) throws JsonProcessingException {
        SearchBookingsResponse searchBookingsResponse =
                objectMapper.getObjectMapperWithNecessaryModule().readValue(response, SearchBookingsResponse.class);
        consoleResultView.display(searchBookingsResponse);
    }
}
