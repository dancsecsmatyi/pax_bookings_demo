// src/test/java/validator/CreateBookingDtoValidatorTest.java
package validator;

import model.dto.CreateBookingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateBookingDtoValidatorTest {

    private CreateBookingDtoValidator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator javaxValidator = factory.getValidator();
        validator = new CreateBookingDtoValidator(javaxValidator);
    }

    @Test
    public void testValidCreateBookingDto() {
        CreateBookingDto dto = new CreateBookingDto("John Doe", LocalDateTime.now(), List.of("NYC"));
        Set<ConstraintViolation<CreateBookingDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Expected no constraint violations");
    }

    @Test
    public void testEmptyPaxName() {
        CreateBookingDto dto = new CreateBookingDto("", LocalDateTime.now(), List.of("NYC"));
        Set<ConstraintViolation<CreateBookingDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size(), "Expected one constraint violation");
        assertEquals("Passenger name must not be empty", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullDeparture() {
        CreateBookingDto dto = new CreateBookingDto("John Doe", null, List.of("NYC"));
        Set<ConstraintViolation<CreateBookingDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size(), "Expected one constraint violation");
        assertEquals("Departure time must not be null", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmptyItinerary() {
        CreateBookingDto dto = new CreateBookingDto("John Doe", LocalDateTime.now(), List.of());
        Set<ConstraintViolation<CreateBookingDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size(), "Expected one constraint violation");
        assertEquals("Itinerary must contain at least one destination", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullItinerary() {
        CreateBookingDto dto = new CreateBookingDto("John Doe", LocalDateTime.now(), null);
        Set<ConstraintViolation<CreateBookingDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size(), "Expected one constraint violation");
        assertEquals("Itinerary must not be null", violations.iterator().next().getMessage());
    }
}