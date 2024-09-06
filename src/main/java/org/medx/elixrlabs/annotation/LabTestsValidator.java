package org.medx.elixrlabs.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;

public class LabTestsValidator implements ConstraintValidator<LabTests, List<Long>> {

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
