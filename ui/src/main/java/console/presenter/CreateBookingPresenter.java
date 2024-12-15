package console.presenter;

import com.fasterxml.jackson.core.JsonProcessingException;
import createbooking.boundary.CreateBookingOutputBoundary;
import model.response.CreateBookingResponse;
import mapper.BookingRequestResponseObjectMapper;
import console.view.ConsoleResultView;

public class CreateBookingPresenter implements CreateBookingOutputBoundary {

    private final BookingRequestResponseObjectMapper objectMapper;
    private final ConsoleResultView consoleResultView;

    public CreateBookingPresenter(BookingRequestResponseObjectMapper objectMapper, ConsoleResultView consoleResultView) {
        this.objectMapper = objectMapper;
        this.consoleResultView = consoleResultView;
    }

    @Override
    public void present(String response) throws JsonProcessingException {
        CreateBookingResponse createBookingResponse =
                objectMapper
                        .getObjectMapperWithNecessaryModule()
                        .readValue(response, CreateBookingResponse.class);
        consoleResultView.display(createBookingResponse);
    }
}
