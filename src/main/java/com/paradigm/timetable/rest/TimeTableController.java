package com.paradigm.timetable.rest;

import com.paradigm.timetable.domain.UnderwriterAllocation;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/uw")
public class TimeTableController {

    @Autowired
    private SolverManager<UnderwriterAllocation, UUID> solverManager;

    @PostMapping("/allocate")
    public UnderwriterAllocation solve(@RequestBody UnderwriterAllocation problem) {
        UUID problemId = UUID.randomUUID();
        // Submit the problem to start solving
        SolverJob<UnderwriterAllocation, UUID> solverJob = solverManager.solve(problemId, problem);
        UnderwriterAllocation solution;
        try {
            // Wait until the solving ends
            solution = solverJob.getFinalBestSolution();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solving Failed.", e);
        }
        return solution;
    }
}
