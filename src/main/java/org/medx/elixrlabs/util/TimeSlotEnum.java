package org.medx.elixrlabs.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeSlotEnum {
    SEVEN_AM("7AM"),
    EIGHT_AM("8AM"),
    NINE_AM("9AM"),
    TEN_AM("10AM"),
    ELEVEN_AM("11AM"),
    TWELVE_PM("12PM"),
    TWO_PM("2PM"),
    THREE_PM("3PM"),
    FOUR_PM("4PM"),
    FIVE_PM("5PM"),
    SIX_PM("6PM"),
    SEVEN_PM("7PM");

    private final String time;
}
