package entity;

import constant.CommonConstants;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class Booking {
    private String uuid;
    private String paxName;
    @DateTimeFormat(pattern = CommonConstants.DATE_TIME_FORMAT)
    private LocalDateTime departure;
    private List<String> itinerary;

    public Booking(String uuid, String paxName, LocalDateTime departure, List<String> itinerary) {
        this.uuid = uuid;
        this.paxName = paxName;
        this.departure = departure;
        this.itinerary = itinerary;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
