/*
package com.paradigm.timetable.solver;

import com.paradigm.timetable.domain.Lesson;
import com.paradigm.timetable.domain.TimeTable;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

import java.util.List;
import java.util.Objects;

*/
/**
 * Hard constraints always outweigh soft constraints.
 * Unfortunately that does not scale well, because it is non-incremental: every time a lesson is assigned
 * to a different time slot or room, all lessons are re-evaluated to calculate the new score.
 *//*

public class TimeTableEasyScoreCalculator implements EasyScoreCalculator<TimeTable, HardSoftScore> {

    @Override
    public HardSoftScore calculateScore(TimeTable timeTable) {
        List<Lesson> lessons = timeTable.getLessonList();
        int hardScore = 0;
        for (Lesson a : lessons) {
            for (Lesson b : lessons) {
                if (Objects.nonNull(a.getTimeslot()) && a.getTimeslot().equals(b.getTimeslot()) && a.getId() < b.getId()) {
                    // A room can accommodate at most one lesson at the same time.
                    if (Objects.nonNull(a.getRoom()) && a.getRoom().equals(b.getRoom())) {
                        hardScore--;
                    }
                    // A teacher can teach at most one lesson at the same time.
                    if (Objects.equals(a.getSubject(), b.getSubject())) {
                        hardScore--;
                    }
                    // A student can attend at most one lesson at the same time.
                    if (Objects.equals(a.getStudentGroup(), b.getStudentGroup())) {
                        hardScore--;
                    }
                }
            }
        }
        int softScore = 0;
        // Soft constraints are only implemented in the "complete" implementation
        return HardSoftScore.of(hardScore, softScore);
    }
}
*/
