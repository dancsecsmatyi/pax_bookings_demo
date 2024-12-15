package model.request;

import constant.CommonConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class SearchBookingsByDepartureRequest {

    @NotNull(message = "Departure time must not be null")
    @Pattern(regexp = CommonConstants.LOCAL_DATE_TIME_REGEX, message = "Departure time must be in the format yyyy-MM-dd'T'HH:mm")
    private String departureTime;

    public SearchBookingsByDepartureRequest() {}

    public SearchBookingsByDepartureRequest(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
}
