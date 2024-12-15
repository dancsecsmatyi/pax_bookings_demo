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
       
        Booking booking = new Booking(UUID.randomUUID().toString(), "John Doe", LocalDateTime.of(2023, 10, 10, 10, 0), List.of("NYC", "LAX"));
        String expectedString = booking.getUuid() + ",John Doe,2023-10-10T10:00,NYC;LAX";

        String result = BookingMapper.bookingToString(booking);

        assertEquals(expectedString, result);
    }

    @Test
    public void testStringToBooking() {
       
        String bookingString = "565110d1-ed7f-4808-bced-38c1157f6e61,John Doe,2023-10-10T10:00,NYC;LAX";
        Booking expectedBooking = new Booking("565110d1-ed7f-4808-bced-38c1157f6e61", "John Doe", LocalDateTime.of(2023, 10, 10, 10, 0), List.of("NYC", "LAX"));

        Booking result = BookingMapper.stringToBooking(bookingString);

        assertEquals(expectedBooking.getUuid(), result.getUuid());
        assertEquals(expectedBooking.getPaxName(), result.getPaxName());
        assertEquals(expectedBooking.getDeparture(), result.getDeparture());
        assertEquals(expectedBooking.getItinerary(), result.getItinerary());
    }
}