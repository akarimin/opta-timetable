package com.paradigm.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Because no Timeslot instances change during solving, a Timeslot is called a problem fact.
 * Such classes do not require any OptaPlanner specific annotations.
 */
@Entity
public class Priority extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long priority;

    public Priority() {
    }

    public Priority(Long priority) {
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public Priority setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Priority{" +
            "id=" + id +
            ", priority=" + priority +
            '}';
    }
}
