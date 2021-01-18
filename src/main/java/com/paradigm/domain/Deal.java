package com.paradigm.domain;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

@Entity
@PlanningEntity
public class Deal extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // not-null when uninitialized
    private String dealId;
    // not-null when uninitialized
    private String broker;
    // not-null when uninitialized
    private String brand;
    // not-null when uninitialized
    private Duration expectedSLA;

    // --> Changes during planning
    // null when uninitialized - needs to be assigned at the end of solution
    // Multiple Lessons will be held in one timeslot and HOPEFULLY not in a same room
    @ManyToOne
    @PlanningVariable(valueRangeProviderRefs = "priorityRange")
    private Priority priority;
    // --> Changes during planning
    // null when uninitialized - needs to be assigned at the end of solution
    // Multiple Lessons will be held in one room and HOPEFULLY not in a same timeSlot
    @ManyToOne
    @PlanningVariable(valueRangeProviderRefs = "queueGroupRange")
    private QueueGroup queueGroup;

    public Deal() {
    }

    public Deal(String dealId, String broker, String brand, Duration expectedSLA) {
        this.dealId = dealId;
        this.broker = broker;
        this.brand = brand;
        this.expectedSLA = expectedSLA;
    }

    public Long getId() {
        return id;
    }

    public Deal setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDealId() {
        return dealId;
    }

    public String getBroker() {
        return broker;
    }

    public String getBrand() {
        return brand;
    }

    public Priority getPriority() {
        return priority;
    }

    public Duration getExpectedSLA() {
        return expectedSLA;
    }

    public QueueGroup getQueueGroup() {
        return queueGroup;
    }

    public Deal setPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public Deal setQueueGroup(QueueGroup queueGroup) {
        this.queueGroup = queueGroup;
        return this;
    }
}
