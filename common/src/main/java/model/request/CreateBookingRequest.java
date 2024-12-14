package model.request;

import java.time.LocalDateTime;
import java.util.List;

public record CreateBookingRequest(
        String paxName, LocalDateTime departure, List<String> itinerary) {}
