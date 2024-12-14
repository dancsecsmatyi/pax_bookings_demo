// repository/src/test/java/mapper/BookingMapperTest.java
package mapper;

import entity.Booking;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingMapperTest {

    @Test
    public void testBookingToString() {
        // Arrange
        Booking booking = new Booking(UUID.randomUUID().toString(), "John Doe", LocalDateTime.of(2023, 10, 10, 10, 0), List.of("NYC", "LAX"));
        String expectedString = booking.uuid() + ",John Doe,2023-10-10T10:00,NYC;LAX";

        // Act
        String result = BookingMapper.bookingToString(booking);

        // Assert
        assertEquals(expectedString, result);
    }

    @Test
    public void testStringToBooking() {
        // Arrange
        String bookingString = "565110d1-ed7f-4808-bced-38c1157f6e61,John Doe,2023-10-10T10:00,NYC;LAX";
        Booking expectedBooking = new Booking("565110d1-ed7f-4808-bced-38c1157f6e61", "John Doe", LocalDateTime.of(2023, 10, 10, 10, 0), List.of("NYC", "LAX"));

        // Act
        Booking result = BookingMapper.stringToBooking(bookingString);

        // Assert
        assertEquals(expectedBooking.uuid(), result.uuid());
        assertEquals(expectedBooking.paxName(), result.paxName());
        assertEquals(expectedBooking.departure(), result.departure());
        assertEquals(expectedBooking.itinerary(), result.itinerary());
    }
}