package org.medx.elixrlabs.annotation;

import java.util.HashSet;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * <p>
 * This class is responsible for validating a lab tests
 * in test packages that tests not be null, test can't be empty
 * and it must be unique.
 * </p>
 */
public class LabTestsValidator implements ConstraintValidator<LabTests, List<Long>> {

    /**
     * Validates if the provided list of tests is part of the valid tests.
     *
     * @param labTests The list of labTests that needs to be validated.
     * @param context Context in which the constraint is evaluated.
     * @return true if the labTests is valid; false otherwise.
     */
    @Override
    public boolean isValid(List<Long> labTests, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (null == labTests) {
            context.buildConstraintViolationWithTemplate("LabTest ID's must not be null")
                    .addConstraintViolation();
            return false;
        }
        if (labTests.isEmpty()) {
            context.buildConstraintViolationWithTemplate("Test Package must contain at least one Lab Test")
                    .addConstraintViolation();
            return false;
        }
        if (new HashSet<>(labTests).size() != labTests.size()) {
            context.buildConstraintViolationWithTemplate("Lab Test ID's must be unique")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
