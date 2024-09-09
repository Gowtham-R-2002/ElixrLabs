package org.medx.elixrlabs.util;

import java.time.LocalDate;

/**
 * <p>This class provides methods for handling common date operations, such as
 * calculating age based on the date of birth</p>
 *
 * @author Gowtham R
 */

public class DateUtil {

    /**
     * Calculates age based on the provided date of birth
     *
     * @param dateOfBirth represents date of birth of the user
     * @return Calculate age of the user
     */
    public static int getAge(LocalDate dateOfBirth) {
        return LocalDate.now().compareTo(dateOfBirth);
    }
}
