package com.paradigm.timetable.domain;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.io.Serializable;
import java.util.List;

@PlanningSolution
public class TimeTable implements Serializable {

    // problem facts
    @ValueRangeProvider(id = "timeSlotRange")
    @ProblemFactCollectionProperty
    private List<Timeslot> timeslotList;

    // problem facts
    @ValueRangeProvider(id = "roomRange")
    @ProblemFactCollectionProperty
    private List<Room> roomList;

    // planning entities, because they change during solving.
    // also the output of the solution
    @PlanningEntityCollectionProperty
    private List<Lesson> lessonList;

    // the other output of the solution
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
