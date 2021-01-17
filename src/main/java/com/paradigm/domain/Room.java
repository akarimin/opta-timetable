package com.paradigm.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Because no Room instances change during solving, a Room is called a problem fact.
 * Such classes do not require any OptaPlanner specific annotations.
 */
@Entity
public class Room extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public Room() {
    }

    public Room(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Room setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Room{" +
            "name='" + name + '\'' +
            '}';
    }
}
