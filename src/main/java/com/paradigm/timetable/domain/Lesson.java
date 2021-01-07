package com.paradigm.timetable.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 * During solving, OptaPlanner changes the timeslot and room fields of the Lesson class, to assign
 * each lesson to a time slot and a room. Because OptaPlanner changes these fields,
 * Lesson is a planning entity.
 * <p>
 * The Lesson class has an @PlanningEntity annotation, so OptaPlanner knows that this class changes during
 * solving because it contains one or more planning variables.
 * <p>
 * Of each Lesson:
 * The values of the timeslot and room fields are typically still null, so unassigned. They are planning variables.
 * The other fields, such as subject, teacher and studentGroup, are filled in. These fields are problem properties.
 */
@PlanningEntity
public class Lesson {

    private Long id;
    // not-null when uninitialized
    private String subject;
    // not-null when uninitialized
    private String teacher;
    // not-null when uninitialized
    private String studentGroup;
    // null when uninitialized - needs to be assigned at the end of solution
    @PlanningVariable(valueRangeProviderRefs = "timeSlotRange")
    private Timeslot timeslot;
    // null when uninitialized - needs to be assigned at the end of solution
    @PlanningVariable(valueRangeProviderRefs = "roomRange")
    private Room room;

    public Lesson() {
    }

    public Lesson(Long id, String subject, String teacher, String studentGroup) {
        this.id = id;
        this.subject = subject;
        this.teacher = teacher;
        this.studentGroup = studentGroup;
    }

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public Lesson setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public Lesson setRoom(Room room) {
        this.room = room;
        return this;
    }

    @Override
    public String toString() {
        return  subject + "(" + id + ")";
    }
}
