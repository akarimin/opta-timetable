package com.paradigm;

import com.paradigm.domain.Lesson;
import com.paradigm.domain.Room;
import com.paradigm.domain.TimeTable;
import com.paradigm.domain.Timeslot;
import org.optaplanner.core.api.solver.SolverManager;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/timeTable")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TimeTableResource {

    @Inject
    SolverManager<TimeTable, Long> solverManager;

    @GET
    public TimeTable get() {
        return load(1L);
    }

    @POST
    @Path("/solve")
    public void solve() {
        solverManager.solveAndListen(1L,
            this::load,
            this::save);
    }

    @Transactional
    protected TimeTable load(Long aLong) {
        return new TimeTable(
            Timeslot.listAll(),
            Room.listAll(),
            Lesson.listAll()
        );
    }

    // why protected ?
    @Transactional
    protected void save(TimeTable timeTable) {
        for (Lesson lesson: timeTable.getLessonList()) {
            Lesson attachedLesson = Lesson.findById(lesson.getId());
            attachedLesson.setTimeslot(lesson.getTimeslot());
            attachedLesson.setRoom(lesson.getRoom());
        }
    }
}