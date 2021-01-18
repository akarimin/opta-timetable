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
public class PrioritizedNewDealsQueue {

    @ValueRangeProvider(id = "priorityRange")
    private List<Priority> priorityList;
    @ValueRangeProvider(id = "queueGroupRange")
    private List<QueueGroup> queueGroupList;
    @PlanningEntityCollectionProperty
    private List<Deal> dealList;
    @PlanningScore
    private HardSoftScore score;

    public PrioritizedNewDealsQueue() {
    }

    public PrioritizedNewDealsQueue(List<Priority> priorityList, List<QueueGroup> queueGroupList, List<Deal> dealList) {
        this.priorityList = priorityList;
        this.queueGroupList = queueGroupList;
        this.dealList = dealList;
    }

    public List<Priority> getPriorityList() {
        return priorityList;
    }

    public List<QueueGroup> getQueueGroupList() {
        return queueGroupList;
    }

    public List<Deal> getDealList() {
        return dealList;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public PrioritizedNewDealsQueue setScore(HardSoftScore score) {
        this.score = score;
        return this;
    }

    @Override
    public String toString() {
        return "PrioritizedNewDealsQueue{" +
            "priorityList=" + priorityList +
            ", queueGroupList=" + queueGroupList +
            ", dealList=" + dealList +
            ", score=" + score +
            '}';
    }
}
