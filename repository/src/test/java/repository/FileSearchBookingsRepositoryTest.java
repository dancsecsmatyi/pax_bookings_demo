package repository;

import entity.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileSearchBookingsRepositoryTest {

    private FileSearchBookingsRepository repository;
    private final String testFilePath = "src/test/resources/test_bookings.csv";

    @BeforeEach
    public void setUp() throws IOException {
        writeTestFile();
        repository = new FileSearchBookingsRepository(testFilePath);
    }

    @Test
    public void testFindByDepartureBefore() {
        LocalDateTime departureTime = LocalDateTime.of(2023, 10, 1, 12, 0);
        List<Booking> bookings = repository.findByDepartureBefore(departureTime);

        assertEquals(2, bookings.size());
        assertEquals("John Doe", bookings.get(0).getPaxName());
        assertEquals("Jane Smith", bookings.get(1).getPaxName());
    }

    @Test
    public void testFindByItineraryContainingAirports() {
        List<Booking> bookings = repository.findByItineraryContainingAirports("AMS", "LHR");

        assertEquals(1, bookings.size());
        assertEquals("Bob Brown", bookings.get(0).getPaxName());
    }

    @Test
    public void testFindByItineraryContainingAirportsReverseOrder() {
        List<Booking> bookings = repository.findByItineraryContainingAirports("LHR", "AMS");

        assertEquals(1, bookings.size());
        assertEquals("Charlie Davis", bookings.get(0).getPaxName());
    }

    @Test
    public void testFindByItineraryContainingAirportsNoMatch() {
        List<Booking> bookings = repository.findByItineraryContainingAirports("NYC", "SEA");

        assertEquals(0, bookings.size());
    }

    @Test
    public void testFindByItineraryContainingAirportsMultipleMatches() {
        try (FileWriter writer = new FileWriter(testFilePath, true)) {
            writer.write("565110d1-ed7f-4808-bced-38c1157f6e61,Eve Adams,2023-10-05T14:00,AMS;LHR;AMS\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Booking> bookings = repository.findByItineraryContainingAirports("AMS", "LHR");

        assertEquals(2, bookings.size());
        assertEquals("Bob Brown", bookings.get(0).getPaxName());
        assertEquals("Eve Adams", bookings.get(1).getPaxName());
    }

    private void writeTestFile() throws IOException {
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("565110d1-ed7f-4808-bced-38c1157f6e61,John Doe,2023-10-01T12:00,NYC;LAX\n");
            writer.write("d2a110d1-ed7f-4808-bced-38c1157f6e62,Jane Smith,2023-09-30T15:00,SFO;SEA\n");
            writer.write("a3b110d1-ed7f-4808-bced-38c1157f6e63,Alice Johnson,2023-10-02T09:00,BOS;MIA\n");
            writer.write("b4c110d1-ed7f-4808-bced-38c1157f6e64,Bob Brown,2023-10-03T10:00,AMS;LHR\n");
            writer.write("c5d110d1-ed7f-4808-bced-38c1157f6e65,Charlie Davis,2023-10-04T11:00,LHR;AMS\n");
        }
    }
}