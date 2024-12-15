package model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SearchBookingsByAirportsRequest {


    @NotEmpty(message = "Origin airport code must not be empty")
    @NotNull(message = "Origin airport code must not be null")
    private String airportFirst;

    @NotEmpty(message = "Destination airport code must not be empty")
    @NotNull(message = "Destination airport code must not be null")
    private String airportSecond;

    public SearchBookingsByAirportsRequest() {
    }

    public SearchBookingsByAirportsRequest(String airportFirst, String airportSecond) {
        this.airportFirst = airportFirst;
        this.airportSecond = airportSecond;
    }

    public String getAirportFirst() {
        return airportFirst;
    }

    public void setAirportFirst( String airportFirst) {
        this.airportFirst = airportFirst;
    }

    public  String getAirportSecond() {
        return airportSecond;
    }

    public void setAirportSecond( String airportSecond) {
        this.airportSecond = airportSecond;
    }
}