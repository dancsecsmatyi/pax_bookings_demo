// common/src/test/java/mapper/BookingRequestResponseObjectMapperTest.java
package mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingRequestResponseObjectMapperTest {

    @Test
    public void testGetObjectMapperWithNecessaryModule() throws Exception {
        // Arrange
        BookingRequestResponseObjectMapper mapper = new BookingRequestResponseObjectMapper();
        ObjectMapper objectMapper = mapper.getObjectMapperWithNecessaryModule();

        // Act
        String dateTimeString = "2023-10-10T10:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
        String serializedDateTime = objectMapper.writeValueAsString(dateTime);
        LocalDateTime deserializedDateTime = objectMapper.readValue(serializedDateTime, LocalDateTime.class);

        // Assert
        assertEquals(dateTime, deserializedDateTime);
    }
}