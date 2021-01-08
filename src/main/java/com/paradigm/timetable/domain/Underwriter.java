package com.paradigm.timetable.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.time.Duration;

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
public class Underwriter {

    private Long id;
    // not-null when uninitialized
    private String groupId;
    // null when uninitialized - needs to be assigned at the end of solution
    @PlanningVariable(valueRangeProviderRefs = "timeSlotRange")
    private Timeslot timeslot;
    // null when uninitialized - needs to be assigned at the end of solution
    @PlanningVariable(valueRangeProviderRefs = "dealRange")
    private Deal deal;

    public Underwriter() {
    }

    public Underwriter(Long id, Duration duration, String groupId) {
        this.id = id;
        this.groupId = groupId;
    }

    public Long getId() {
        return id;
    }

    public String getGroupId() {
        return groupId;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public Underwriter setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
        return this;
    }

    public Deal getDeal() {
        return deal;
    }

    public Underwriter setDeal(Deal deal) {
        this.deal = deal;
        return this;
    }

    @Override
    public String toString() {
        return  groupId + "(" + id + ")";
    }
}
