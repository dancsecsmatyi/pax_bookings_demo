package model.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SearchBookingDto(String paxName, LocalDateTime departure, List<String> itinerary) {
}