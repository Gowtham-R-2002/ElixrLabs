package org.medx.elixrlabs.annotation;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.medx.elixrlabs.util.TimeSlotEnum;

/**
 * <p>This class is responsible for validating a time slot
 * against predefined valid time slots. It ensures that
 * the input time string matches one of the allowed time slots.</p>
 *
 * @author Gowtham R
 */
public class TimeSlotValidator implements ConstraintValidator<TimeSlot, String> {

    /**
     * <p>Validates if the provided time is part of the valid time slots.</p>
     *
     * @param time The time string that needs to be validated.
     * @param constraintValidatorContext Context in which the constraint is evaluated.
     * @return true if the time is valid; false otherwise.
     */
    @Override
    public boolean isValid(String time, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext
                .buildConstraintViolationWithTemplate("Invalid Slot! Must be one among the following !"
                        + Arrays.stream(TimeSlotEnum.values())
                        .map(TimeSlotEnum::getTime)
                        .toList()).addConstraintViolation();
        return Arrays.stream(TimeSlotEnum.values())
                .map(TimeSlotEnum::getTime)
                .toList()
                .contains(time);
    }
}
