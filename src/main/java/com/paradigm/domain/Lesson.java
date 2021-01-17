package com.paradigm.domain;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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

@Entity
@PlanningEntity
public class Lesson extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // not-null when uninitialized
    private String subject;         // Math, French, ...
    // not-null when uninitialized
    private String teacher;         // A. Turing
    // not-null when uninitialized
    private String studentGroup;    // The 9th Grade

    // --> Changes during planning
    // null when uninitialized - needs to be assigned at the end of solution
    // Multiple Lessons will be held in one timeslot and HOPEFULLY not in a same room
    @ManyToOne
    @PlanningVariable(valueRangeProviderRefs = "timeSlotRange")
    private Timeslot timeslot;
    // --> Changes during planning
    // null when uninitialized - needs to be assigned at the end of solution
    // Multiple Lessons will be held in one room and HOPEFULLY not in a same timeSlot
    @ManyToOne
    @PlanningVariable(valueRangeProviderRefs = "roomRange")
    private Room room;

    public Lesson() {
    }

    public Lesson(String subject, String teacher, String studentGroup) {
        this.subject = subject;
        this.teacher = teacher;
        this.studentGroup = studentGroup;
    }

    public Long getId() {
        return id;
    }

    public Lesson setId(Long id) {
        this.id = id;
        return this;
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

    public Room getRoom() {
        return room;
    }

    public Lesson setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
        return this;
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
