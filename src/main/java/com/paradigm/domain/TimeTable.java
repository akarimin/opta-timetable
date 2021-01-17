package com.paradigm.domain;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

/**
 * Built-in Hard-constraint: Domain constraints are changed by optaPlanner and it is only assigned one time.
 */
@PlanningSolution
public class TimeTable {

    @ValueRangeProvider(id = "timeSlotRange")
    private List<Timeslot> timeslotList;
    @ValueRangeProvider(id = "roomRange")
    private List<Room> roomList;
    @PlanningEntityCollectionProperty
    private List<Lesson> lessonList;
    @PlanningScore
    private HardSoftScore score;

    public TimeTable() {
    }

    public TimeTable(List<Timeslot> timeslotList, List<Room> roomList, List<Lesson> lessonList) {
        this.timeslotList = timeslotList;
        this.roomList = roomList;
        this.lessonList = lessonList;
    }

    public List<Timeslot> getTimeslotList() {
        return timeslotList;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public HardSoftScore getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
            "timeslotList=" + timeslotList +
            ", roomList=" + roomList +
            ", lessonList=" + lessonList +
            ", score=" + score +
            '}';
    }
}
