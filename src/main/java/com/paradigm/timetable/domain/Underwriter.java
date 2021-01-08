package com.paradigm.timetable.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.time.Duration;

/**
 * During solving, OptaPlanner changes the timeslot and deal fields of the Underwriter class, to assign
 * each Underwriter to a time slot and a deal. Because OptaPlanner changes these fields,
 * Underwriter is a planning entity.
 * <p>
 * The Underwriter class has an @PlanningEntity annotation, so OptaPlanner knows that this class changes during
 * solving because it contains one or more planning variables.
 * <p>
 * Of each Underwriter:
 * The values of the timeslot and deal fields are typically still null, so unassigned. They are planning variables.
 * The other fields, such as groupId are filled in. These fields are problem properties.
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
