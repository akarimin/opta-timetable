package com.paradigm.timetable.domain;

/**
 * Because no Room instances change during solving, a Room is called a problem fact.
 * Such classes do not require any OptaPlanner specific annotations.
 */
public class Room {

    private String name;

    public Room() {
    }

    public Room(String name) {
        this.name = name;
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
