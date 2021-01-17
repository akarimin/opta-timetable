package com.paradigm.solver;

import com.paradigm.domain.Lesson;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

public class TimeTableConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
            roomConflict(constraintFactory),
            teacherConflict(constraintFactory),
            studentGroupConflict(constraintFactory),
            teacherCompactness(constraintFactory)
        };
    }

    // Soft Constraint
    private Constraint teacherCompactness(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Lesson.class)
            .join(Lesson.class,
                Joiners.equal(Lesson::getTeacher))
            .filter((lesson1, lesson2) -> lesson1.getTimeslot().getEndTime()
                .equals(lesson2.getTimeslot().getStartTime()))
            .reward("Teacher Compactness Reward", HardSoftScore.ofSoft(10));
    }

    private Constraint studentGroupConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Lesson.class)
            .join(Lesson.class,
                Joiners.equal(Lesson::getStudentGroup),
                Joiners.equal(Lesson::getTimeslot))
            .penalize("Student Group conflict", HardSoftScore.ONE_HARD);
    }

    private Constraint teacherConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Lesson.class)
            .join(Lesson.class,
                Joiners.equal(Lesson::getTeacher),
                Joiners.equal(Lesson::getTimeslot))
            .penalize("Teacher conflict", HardSoftScore.ONE_HARD);
    }

    private Constraint roomConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Lesson.class)
            .join(Lesson.class,
                Joiners.equal(Lesson::getRoom),
                Joiners.equal(Lesson::getTimeslot))
            .penalize("Room conflict", HardSoftScore.ONE_HARD);
    }
}
