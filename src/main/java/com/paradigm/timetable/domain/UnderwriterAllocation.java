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
public class UnderwriterAllocation implements Serializable {

    // problem facts
    @ValueRangeProvider(id = "timeSlotRange")
    @ProblemFactCollectionProperty
    private List<Timeslot> timeslotList;

    // problem facts
    @ValueRangeProvider(id = "dealRange")
    @ProblemFactCollectionProperty
    private List<Deal> dealList;

    // planning entities, because they change during solving.
    // also the output of the solution
    @PlanningEntityCollectionProperty
    private List<Underwriter> underwriterList;

    // the other output of the solution
    @PlanningScore
    private HardSoftScore score;

    public UnderwriterAllocation() {
    }

    public UnderwriterAllocation(List<Timeslot> timeslotList, List<Deal> dealList, List<Underwriter> underwriterList) {
        this.timeslotList = timeslotList;
        this.dealList = dealList;
        this.underwriterList = underwriterList;
    }

    public List<Timeslot> getTimeslotList() {
        return timeslotList;
    }

    public List<Deal> getDealList() {
        return dealList;
    }

    public List<Underwriter> getUnderwriterList() {
        return underwriterList;
    }

    public HardSoftScore getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
            "timeslotList=" + timeslotList +
            ", dealList=" + dealList +
            ", underwriterList=" + underwriterList +
            ", score=" + score +
            '}';
    }
}
