package presenter;

import com.fasterxml.jackson.core.JsonProcessingException;
import createbooking.boundary.CreateBookingOutputBoundary;
import model.response.CreateBookingResponse;
import mapper.BookingRequestResponseObjectMapper;
import view.ConsoleResultView;

public class CreateBookingPresenter implements CreateBookingOutputBoundary {

  BookingRequestResponseObjectMapper objectMapper = new BookingRequestResponseObjectMapper();

  private final ConsoleResultView consoleResultView;

    public CreateBookingPresenter(ConsoleResultView consoleResultView) {
        this.consoleResultView = consoleResultView;
    }

    @Override
  public void present(String response) throws JsonProcessingException {
    CreateBookingResponse createBookingResponse =
        objectMapper.getObjectMapperWithNecessaryModule().readValue(response, CreateBookingResponse.class);
    consoleResultView.display(createBookingResponse);
  }
}
