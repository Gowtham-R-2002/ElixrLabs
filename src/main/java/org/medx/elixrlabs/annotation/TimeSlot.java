package org.medx.elixrlabs.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>This annotation is used to validate time slots for a specific field.
 * It ensures that the annotated field meets the required criteria
 * defined in the associated validator.</p>
 *
 * @author Gowtham R
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = TimeSlotValidator.class)
public @interface TimeSlot {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
