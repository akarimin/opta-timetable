package com.paradigm.timetable.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Because no Timeslot instances change during solving, a Timeslot is called a problem fact.
 * Such classes do not require any OptaPlanner specific annotations.
 */
public class Timeslot {

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public Timeslot() {
    }

    public Timeslot(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return dayOfWeek + " " + startTime.toString();
    }
}
