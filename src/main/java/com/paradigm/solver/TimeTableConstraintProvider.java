package com.paradigm.solver;

import com.paradigm.domain.Deal;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

import java.time.Duration;
import java.util.Objects;

public class TimeTableConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
//            uwAvailabilityConflict(constraintFactory),
            queueGroupByBrandReward(constraintFactory),
            priorityConflict(constraintFactory)
        };
    }

    // Soft Constraint
    /*private Constraint queueGroupConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Deal.class)
            .join(Deal.class,
                Joiners.equal(Deal::getQueueGroup))
            .filter((group1, group2) -> !group1.getBrand().equals(group2.getBrand()))
            .penalize("Group By Broker Conflict", HardSoftScore.ONE_HARD);
    }*/

    // Soft Constraint
    /*private Constraint queueGroupMapping(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Deal.class)
            .join(Deal.class,
                Joiners.equal(Deal::getQueueGroup))
            .filter((group1, group2) -> group1.getBrand().equals(group2.getBrand()))
            .reward("Group By Broker Reward", HardSoftScore.ofSoft(10));
    }*/

    /*private Constraint queueGroupReward(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Deal.class)
            .join(Deal.class,
                Joiners.equal(Deal::getQueueGroup))
            .filter((d1, d2) -> Duration.between(d1.getUwAvailability().getStartTime(),
                d1.getUwAvailability().getEndTime()).compareTo(d1.getExpectedSLA()) >=
                Duration.between(d2.getUwAvailability().getStartTime(),
                    d2.getUwAvailability().getEndTime()).compareTo(d2.getExpectedSLA()))
            .reward("UW Availability Reward", HardSoftScore.ONE_HARD);
    }*/

    private Constraint priorityConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Deal.class)
            .join(Deal.class,
                Joiners.equal(Deal::getPriority))
            .filter((d1, d2) -> d1.getExpectedSLA().compareTo(d2.getExpectedSLA()) > 0)
            .penalize("Priority conflict", HardSoftScore.ofHard(2));
    }

    private Constraint queueGroupByBrandReward(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Deal.class)
            .join(Deal.class,
                Joiners.equal(Deal::getQueueGroup))
            .filter((d1, d2) -> Objects.equals(d1.getBrand(), d1.getQueueGroup().getBrand()) &&
                                Objects.equals(d2.getBrand(), d2.getQueueGroup().getBrand()))
            .reward("Queue Group by brand mapping", HardSoftScore.ofHard(2));
    }
}
