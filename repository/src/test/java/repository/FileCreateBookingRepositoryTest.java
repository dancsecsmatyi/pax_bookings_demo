// repository/src/test/java/repository/FileCreateBookingRepositoryTest.java
package repository;

import entity.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileCreateBookingRepositoryTest {

    private FileCreateBookingRepository repository;
    private final String testFilePath = "src/test/resources/test_create_bookings.csv";

    @BeforeEach
    public void setUp() throws IOException {
        // Create a test file
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
        testFile.createNewFile();
        repository = new FileCreateBookingRepository(testFilePath);
    }

    @Test
    public void testSave() throws IOException {
        // Arrange
        Booking booking = new Booking(UUID.randomUUID().toString(), "John Doe", LocalDateTime.of(2023, 10, 10, 10, 0), List.of("NYC", "LAX"));

        // Act
        repository.save(booking);

        // Assert
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line = reader.readLine();
            assertEquals(booking.uuid() + ",John Doe,2023-10-10T10:00,NYC;LAX", line);
        }
    }

    @Test
    public void testSaveMultipleBookings() throws IOException {
        // Arrange
        Booking booking1 = new Booking(UUID.randomUUID().toString(), "John Doe", LocalDateTime.of(2023, 10, 10, 10, 0), List.of("NYC", "LAX"));
        Booking booking2 = new Booking(UUID.randomUUID().toString(), "Jane Smith", LocalDateTime.of(2023, 10, 11, 11, 0), List.of("SFO", "SEA"));

        // Act
        repository.save(booking1);
        repository.save(booking2);

        // Assert
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            assertEquals(booking1.uuid() + ",John Doe,2023-10-10T10:00,NYC;LAX", line1);
            assertEquals(booking2.uuid() + ",Jane Smith,2023-10-11T11:00,SFO;SEA", line2);
        }
    }
}