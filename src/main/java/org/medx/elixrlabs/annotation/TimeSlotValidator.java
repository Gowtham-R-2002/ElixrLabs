package org.medx.elixrlabs.annotation;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.medx.elixrlabs.util.TimeSlotEnum;

public class TimeSlotValidator implements ConstraintValidator<TimeSlot, String> {
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
