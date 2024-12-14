package entity;

import java.time.LocalDateTime;
import java.util.List;

public record Booking(String uuid, String paxName, LocalDateTime departure, List<String> itinerary) {
}