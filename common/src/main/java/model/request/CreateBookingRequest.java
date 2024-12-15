package model.request;

import constant.CommonConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CreateBookingRequest {

    @NotEmpty(message = "Passenger name must not be empty")
    @NotNull(message = "Passenger name must not be null")
    private String paxName;

    @NotNull(message = "Departure time must not be null")
    @Pattern(regexp = CommonConstants.LOCAL_DATE_TIME_REGEX, message = "Departure time must be in the format yyyy-MM-dd'T'HH:mm")
    private String departure;

    @NotNull(message = "Itinerary must not be null")
    @Size(min = 1, message = "Itinerary must contain at least one destination")
    private List<String> itinerary;

    public CreateBookingRequest() {
    }

    public CreateBookingRequest(String paxName, String departure, List<String> itinerary) {
        this.paxName = paxName;
        this.departure = departure;
        this.itinerary = itinerary;
    }

    public String getPaxName() {
        return paxName;
    }

    public void setPaxName(String paxName) {
        this.paxName = paxName;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public List<String> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<String> itinerary) {
        this.itinerary = itinerary;
    }
}