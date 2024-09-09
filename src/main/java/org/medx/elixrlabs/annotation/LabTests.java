package org.medx.elixrlabs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * <p>
 * This annotation is used to validate labTests for a specific field.
 * It ensures that the annotated field meets the required criteria
 * defined in the associated validator.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = LabTestsValidator.class)
public @interface LabTests {
    String message() default "Invalid Test ID's";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
