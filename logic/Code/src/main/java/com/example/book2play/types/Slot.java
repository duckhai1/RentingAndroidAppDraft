package com.example.book2play.types;

import java.sql.Time;
import java.util.Objects;

public class Slot {
    private Court court;
    private Time startTime;
    private Time endTime;

    public Slot(Court court, Time startTime, Time endTime) {
        this.court = court;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Court getCourt() {
        return court;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return String.format("Slot(%s, %s, %s)", court, startTime, endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(court, startTime, endTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        var other = (Slot) obj;
        return court.equals(other.getCourt())
                && startTime.equals(other.getStartTime())
                && endTime.equals(other.getEndTime());
    }
}
