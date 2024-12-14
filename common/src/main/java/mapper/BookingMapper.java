// repository/src/main/java/mapper/BookingMapper.java
package mapper;

import entity.Booking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static String bookingToString(Booking booking) {
        return booking.uuid() + "," + booking.paxName().trim() + "," + booking.departure().format(formatter) + "," + String.join(";", booking.itinerary()).trim();
    }

    public static Booking stringToBooking(String line) {
        String[] parts = line.split(",");
        String uuid = parts[0];
        String paxName = parts[1];
        LocalDateTime departure = LocalDateTime.parse(parts[2], formatter);
        List<String> itinerary = List.of(parts[3].split(";"));
        return new Booking(uuid, paxName, departure, itinerary);
    }
}