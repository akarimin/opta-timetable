package com.paradigm;

import com.paradigm.domain.Deal;
import com.paradigm.domain.Priority;
import com.paradigm.domain.PrioritizedNewDealsQueue;
import com.paradigm.domain.QueueGroup;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutionException;

@Path("/timeTable")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TimeTableResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeTableResource.class);

    @Inject
    SolverManager<PrioritizedNewDealsQueue, Long> solverManager;

    @GET
    public PrioritizedNewDealsQueue get() {
        return load(1L);
    }

    @POST
    @Path("/solve")
    public void solve() {
        Deal.deleteAll();
        SolverJob<PrioritizedNewDealsQueue, Long> job = solverManager.solveAndListen(1L,
            this::load,
            this::save);
        try {
            PrioritizedNewDealsQueue finalBestSolution = job.getFinalBestSolution();
            finalBestSolution.setScore(job.getFinalBestSolution().getScore());
            LOGGER.info("Best Solution: {}", finalBestSolution);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    protected PrioritizedNewDealsQueue load(Long aLong) {
        return new PrioritizedNewDealsQueue(
            Priority.listAll(),
            QueueGroup.listAll(),
            Deal.listAll()
        );
    }

    // why protected ?
    @Transactional
    protected void save(PrioritizedNewDealsQueue prioritizedNewDealsQueue) {
        for (Deal deal : prioritizedNewDealsQueue.getDealList()) {
            Deal attachedDeal = Deal.findById(deal.getId());
            attachedDeal.setPriority(deal.getPriority());
            attachedDeal.setQueueGroup(deal.getQueueGroup());
        }
    }
}