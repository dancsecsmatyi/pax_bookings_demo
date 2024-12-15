package mapper;

import constant.CommonConstants;
import entity.Booking;

import java.time.LocalDateTime;
import java.util.List;

public class BookingMapper {
    public static String bookingToString(Booking booking) {
        return booking.getUuid() + "," + booking.getPaxName().trim() + "," + booking.getDeparture() + "," + String.join(";", booking.getItinerary()).trim();
    }

    public static Booking stringToBooking(String line) {
        String[] parts = line.split(",");
        String uuid = parts[0];
        String paxName = parts[1];
        LocalDateTime departure = LocalDateTime.parse(parts[2], CommonConstants.DATE_TIME_FORMATTER);
        List<String> itinerary = List.of(parts[3].split(";"));
        return new Booking(uuid, paxName, departure, itinerary);
    }
}