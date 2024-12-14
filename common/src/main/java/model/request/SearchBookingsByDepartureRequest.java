// src/main/java/search/model/SearchBookingsRequest.java
package model.request;

import java.time.LocalDateTime;

public record SearchBookingsByDepartureRequest(LocalDateTime departureTime) {
}