package model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class CreateBookingDto {

    @NotEmpty(message = "Passenger name must not be empty")
    private String paxName;

    @NotNull(message = "Departure time must not be null")
    private LocalDateTime departure;

    @NotNull(message = "Itinerary must not be null")
    @Size(min = 1, message = "Itinerary must contain at least one destination")
    private List<String> itinerary;

    public CreateBookingDto(String paxName, LocalDateTime departure, List<String> itinerary) {
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

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public List<String> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<String> itinerary) {
        this.itinerary = itinerary;
    }
}