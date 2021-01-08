package com.paradigm.timetable.solver;

import com.paradigm.timetable.domain.Underwriter;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

public class TimeTableConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
            dealConflict(constraintFactory),
            groupIdConflict(constraintFactory),
        };
    }

    private Constraint dealConflict(ConstraintFactory constraintFactory) {
        // An UW can work on one deal at the same time.

        // Select a uw ...
        return constraintFactory.from(Underwriter.class)
            // ... and pair it with another uw ...
            .join(Underwriter.class,
                // ... in the same timeslot ...
                Joiners.equal(Underwriter::getTimeslot),
                // ... in the same deal ...
                Joiners.equal(Underwriter::getDeal),
                // ... and the pair is unique (different id, no reverse pairs)
                Joiners.lessThan(Underwriter::getId))
            // then penalize each pair with a hard weight.
            .penalize("Deal Conflict", HardSoftScore.ONE_HARD);
    }

   /* private Constraint durationConflict(ConstraintFactory constraintFactory) {
        // An UW can be at most within one duration at the same time.
        return constraintFactory.from(Underwriter.class)
            .join(Underwriter.class,
                Joiners.equal(Underwriter::getTimeslot),
                Joiners.equal(Underwriter::getDuration),
                Joiners.lessThan(Underwriter::getId))
            .penalize("Duration Conflict", HardSoftScore.ONE_HARD);
    }*/

    private Constraint groupIdConflict(ConstraintFactory constraintFactory) {
        // An uw can be at least in one group at the same time.
        return constraintFactory.from(Underwriter.class)
            .join(Underwriter.class,
                Joiners.equal(Underwriter::getTimeslot),
                Joiners.equal(Underwriter::getGroupId),
                Joiners.lessThan(Underwriter::getId))
            .penalize("GroupId Conflict", HardSoftScore.ONE_HARD);
    }
}
