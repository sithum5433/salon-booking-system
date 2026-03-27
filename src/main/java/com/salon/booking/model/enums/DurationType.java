package com.salon.booking.model.enums;

public enum DurationType {
    THIRTY_MINUTES(30, "30 Minutes"),
    ONE_HOUR(60, "1 Hour");

    private final int minutes;
    private final String label;

    DurationType(int minutes, String label) {
        this.minutes = minutes;
        this.label = label;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getLabel() {
        return label;
    }
}
