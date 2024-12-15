package model.response;

import java.time.LocalDateTime;
import java.util.List;

public record CreateBookingResponse (Result result, String id, String paxName, LocalDateTime departure, List<String> itinerary) {
}