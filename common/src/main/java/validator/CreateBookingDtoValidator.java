// validator/CreateBookingDtoValidator.java
package validator;

import model.dto.CreateBookingDto;
import org.springframework.stereotype.Component;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;

@Component
public class CreateBookingDtoValidator {

    private final Validator validator;

    public CreateBookingDtoValidator(Validator validator) {
        this.validator = validator;
    }

    public Set<ConstraintViolation<CreateBookingDto>> validate(CreateBookingDto createBookingDto) {
        return validator.validate(createBookingDto);
    }
}