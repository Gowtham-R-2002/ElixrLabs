package org.medx.elixrlabs.util;

import java.time.LocalDate;

public class DateUtil {

    public static int getAge(LocalDate dateOfBirth) {
        return LocalDate.now().compareTo(dateOfBirth);
    }
}
