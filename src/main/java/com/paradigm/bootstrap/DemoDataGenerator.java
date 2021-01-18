package com.paradigm.bootstrap;

import com.paradigm.domain.Deal;
import com.paradigm.domain.Priority;
import com.paradigm.domain.QueueGroup;
import io.quarkus.runtime.StartupEvent;
import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DemoDataGenerator {

    @ConfigProperty(name = "timeTable.demoData", defaultValue = "SMALL")
    DemoData demoData;

    @Transactional
    public void generateDemoData(@Observes StartupEvent startupEvent) {
        if (demoData == DemoData.NONE) {
            return;
        }

        List<Priority> priorityList = new ArrayList<>(10);
        priorityList.add(new Priority(1L));
        priorityList.add(new Priority(2L));
        priorityList.add(new Priority(3L));
        priorityList.add(new Priority(4L));
        priorityList.add(new Priority(5L));
        priorityList.add(new Priority(6L));
        priorityList.add(new Priority(7L));
        priorityList.add(new Priority(8L));
        priorityList.add(new Priority(9L));
        priorityList.add(new Priority(10L));


       /* if (demoData == DemoData.LARGE) {
            expectedSLAList.add(new ExpectedSLA(DayOfWeek.WEDNESDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
            expectedSLAList.add(new ExpectedSLA(DayOfWeek.WEDNESDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
            expectedSLAList.add(new ExpectedSLA(DayOfWeek.WEDNESDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
            expectedSLAList.add(new ExpectedSLA(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
            expectedSLAList.add(new ExpectedSLA(DayOfWeek.WEDNESDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
            expectedSLAList.add(new ExpectedSLA(DayOfWeek.THURSDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
            expectedSLAList.add(new ExpectedSLA(DayOfWeek.THURSDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
            expectedSLAList.add(new ExpectedSLA(DayOfWeek.THURSDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
            expectedSLAList.add(new ExpectedSLA(DayOfWeek.THURSDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
            expectedSLAList.add(new ExpectedSLA(DayOfWeek.THURSDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
            expectedSLAList.add(new ExpectedSLA(DayOfWeek.FRIDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
            expectedSLAList.add(new ExpectedSLA(DayOfWeek.FRIDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
            expectedSLAList.add(new ExpectedSLA(DayOfWeek.FRIDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
            expectedSLAList.add(new ExpectedSLA(DayOfWeek.FRIDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
            expectedSLAList.add(new ExpectedSLA(DayOfWeek.FRIDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
        }*/
        Priority.persist(priorityList);

        List<QueueGroup> queueGroupList = new ArrayList<>(3);
        queueGroupList.add(new QueueGroup("Queue Group 1", "Merix"));
        queueGroupList.add(new QueueGroup("Queue Group 2", "Duca"));
        queueGroupList.add(new QueueGroup("Queue Group 3", "Manulife"));
        /*if (demoData == DemoData.LARGE) {
            queueGroupList.add(new QueueGroup("Room D"));
            queueGroupList.add(new QueueGroup("Room E"));
            queueGroupList.add(new QueueGroup("Room F"));
        }*/
        QueueGroup.persist(queueGroupList);

        List<Deal> dealList = new ArrayList<>(12);
        dealList.add(new Deal("{" + RandomStringUtils.randomAlphanumeric(8) + "}",
            "A. Turing", "Merix", Duration.ofHours(-4)));
        dealList.add(new Deal("{" + RandomStringUtils.randomAlphanumeric(8) + "}",
            "A. Turing", "Merix", Duration.ofHours(-2)));
        dealList.add(new Deal("{" + RandomStringUtils.randomAlphanumeric(8) + "}",
            "M. Curie", "Merix", Duration.ofHours(0)));
        dealList.add(new Deal("{" + RandomStringUtils.randomAlphanumeric(8) + "}",
            "C. Darwin", "Duca", Duration.ofHours(0)));
        dealList.add(new Deal("{" + RandomStringUtils.randomAlphanumeric(8) + "}",
            "I. Jones", "Duca", Duration.ofHours(2)));
        dealList.add(new Deal("{" + RandomStringUtils.randomAlphanumeric(8) + "}",
            "I. Jones", "Duca", Duration.ofHours(2)));
        dealList.add(new Deal("{" + RandomStringUtils.randomAlphanumeric(8) + "}",
            "I. Jones", "Manulife", Duration.ofHours(4)));
        dealList.add(new Deal("{" + RandomStringUtils.randomAlphanumeric(8) + "}",
            "P. Cruz", "Manulife", Duration.ofHours(4)));
        dealList.add(new Deal("{" + RandomStringUtils.randomAlphanumeric(8) + "}",
            "P. Cruz", "Manulife", Duration.ofHours(6)));
        dealList.add(new Deal("{" + RandomStringUtils.randomAlphanumeric(8) + "}",
            "P. Cruz", "Manulife", Duration.ofHours(6)));
        dealList.add(new Deal("{" + RandomStringUtils.randomAlphanumeric(8) + "}",
            "I. Jones", "Manulife", Duration.ofHours(8)));
        dealList.add(new Deal("{" + RandomStringUtils.randomAlphanumeric(8) + "}",
            "C. Darwin", "Duca", Duration.ofHours(8)));
        /*if (demoData == DemoData.LARGE) {
            dealList.add(new Deal("Math", "A. Turing", "9th grade"));
            dealList.add(new Deal("Math", "A. Turing", "9th grade"));
            dealList.add(new Deal("Math", "A. Turing", "9th grade"));
            dealList.add(new Deal("ICT", "A. Turing", "9th grade"));
            dealList.add(new Deal("Physics", "M. Curie", "9th grade"));
            dealList.add(new Deal("Geography", "C. Darwin", "9th grade"));
            dealList.add(new Deal("Geology", "C. Darwin", "9th grade"));
            dealList.add(new Deal("History", "I. Jones", "9th grade"));
            dealList.add(new Deal("English", "I. Jones", "9th grade"));
            dealList.add(new Deal("Drama", "I. Jones", "9th grade"));
            dealList.add(new Deal("Art", "S. Dali", "9th grade"));
            dealList.add(new Deal("Art", "S. Dali", "9th grade"));
            dealList.add(new Deal("Physical education", "C. Lewis", "9th grade"));
            dealList.add(new Deal("Physical education", "C. Lewis", "9th grade"));
            dealList.add(new Deal("Physical education", "C. Lewis", "9th grade"));
        }*/

        /*dealList.add(new Deal(RandomStringUtils.random(8), "A. Turing", "10th grade"));
        dealList.add(new Deal(RandomStringUtils.random(8), "A. Turing", "10th grade"));
        dealList.add(new Deal(RandomStringUtils.random(8), "A. Turing", "10th grade"));
        dealList.add(new Deal(RandomStringUtils.random(8), "M. Curie", "10th grade"));
        dealList.add(new Deal(RandomStringUtils.random(8), "M. Curie", "10th grade"));
        dealList.add(new Deal(RandomStringUtils.random(8), "M. Curie", "10th grade"));
        dealList.add(new Deal(RandomStringUtils.random(8), "C. Darwin", "10th grade"));
        dealList.add(new Deal(RandomStringUtils.random(8), "I. Jones", "10th grade"));
        dealList.add(new Deal(RandomStringUtils.random(8), "P. Cruz", "10th grade"));
        dealList.add(new Deal(RandomStringUtils.random(8), "P. Cruz", "10th grade"));*/

        /*if (demoData == DemoData.LARGE) {
            dealList.add(new Deal("Math", "A. Turing", "10th grade"));
            dealList.add(new Deal("Math", "A. Turing", "10th grade"));
            dealList.add(new Deal("ICT", "A. Turing", "10th grade"));
            dealList.add(new Deal("Physics", "M. Curie", "10th grade"));
            dealList.add(new Deal("Biology", "C. Darwin", "10th grade"));
            dealList.add(new Deal("Geology", "C. Darwin", "10th grade"));
            dealList.add(new Deal("History", "I. Jones", "10th grade"));
            dealList.add(new Deal("English", "P. Cruz", "10th grade"));
            dealList.add(new Deal("English", "P. Cruz", "10th grade"));
            dealList.add(new Deal("Drama", "I. Jones", "10th grade"));
            dealList.add(new Deal("Art", "S. Dali", "10th grade"));
            dealList.add(new Deal("Art", "S. Dali", "10th grade"));
            dealList.add(new Deal("Physical education", "C. Lewis", "10th grade"));
            dealList.add(new Deal("Physical education", "C. Lewis", "10th grade"));
            dealList.add(new Deal("Physical education", "C. Lewis", "10th grade"));

            dealList.add(new Deal("Math", "A. Turing", "11th grade"));
            dealList.add(new Deal("Math", "A. Turing", "11th grade"));
            dealList.add(new Deal("Math", "A. Turing", "11th grade"));
            dealList.add(new Deal("Math", "A. Turing", "11th grade"));
            dealList.add(new Deal("Math", "A. Turing", "11th grade"));
            dealList.add(new Deal("ICT", "A. Turing", "11th grade"));
            dealList.add(new Deal("Physics", "M. Curie", "11th grade"));
            dealList.add(new Deal("Chemistry", "M. Curie", "11th grade"));
            dealList.add(new Deal("French", "M. Curie", "11th grade"));
            dealList.add(new Deal("Physics", "M. Curie", "11th grade"));
            dealList.add(new Deal("Geography", "C. Darwin", "11th grade"));
            dealList.add(new Deal("Biology", "C. Darwin", "11th grade"));
            dealList.add(new Deal("Geology", "C. Darwin", "11th grade"));
            dealList.add(new Deal("History", "I. Jones", "11th grade"));
            dealList.add(new Deal("History", "I. Jones", "11th grade"));
            dealList.add(new Deal("English", "P. Cruz", "11th grade"));
            dealList.add(new Deal("English", "P. Cruz", "11th grade"));
            dealList.add(new Deal("English", "P. Cruz", "11th grade"));
            dealList.add(new Deal("Spanish", "P. Cruz", "11th grade"));
            dealList.add(new Deal("Drama", "P. Cruz", "11th grade"));
            dealList.add(new Deal("Art", "S. Dali", "11th grade"));
            dealList.add(new Deal("Art", "S. Dali", "11th grade"));
            dealList.add(new Deal("Physical education", "C. Lewis", "11th grade"));
            dealList.add(new Deal("Physical education", "C. Lewis", "11th grade"));
            dealList.add(new Deal("Physical education", "C. Lewis", "11th grade"));

            dealList.add(new Deal("Math", "A. Turing", "12th grade"));
            dealList.add(new Deal("Math", "A. Turing", "12th grade"));
            dealList.add(new Deal("Math", "A. Turing", "12th grade"));
            dealList.add(new Deal("Math", "A. Turing", "12th grade"));
            dealList.add(new Deal("Math", "A. Turing", "12th grade"));
            dealList.add(new Deal("ICT", "A. Turing", "12th grade"));
            dealList.add(new Deal("Physics", "M. Curie", "12th grade"));
            dealList.add(new Deal("Chemistry", "M. Curie", "12th grade"));
            dealList.add(new Deal("French", "M. Curie", "12th grade"));
            dealList.add(new Deal("Physics", "M. Curie", "12th grade"));
            dealList.add(new Deal("Geography", "C. Darwin", "12th grade"));
            dealList.add(new Deal("Biology", "C. Darwin", "12th grade"));
            dealList.add(new Deal("Geology", "C. Darwin", "12th grade"));
            dealList.add(new Deal("History", "I. Jones", "12th grade"));
            dealList.add(new Deal("History", "I. Jones", "12th grade"));
            dealList.add(new Deal("English", "P. Cruz", "12th grade"));
            dealList.add(new Deal("English", "P. Cruz", "12th grade"));
            dealList.add(new Deal("English", "P. Cruz", "12th grade"));
            dealList.add(new Deal("Spanish", "P. Cruz", "12th grade"));
            dealList.add(new Deal("Drama", "P. Cruz", "12th grade"));
            dealList.add(new Deal("Art", "S. Dali", "12th grade"));
            dealList.add(new Deal("Art", "S. Dali", "12th grade"));
            dealList.add(new Deal("Physical education", "C. Lewis", "12th grade"));
            dealList.add(new Deal("Physical education", "C. Lewis", "12th grade"));
            dealList.add(new Deal("Physical education", "C. Lewis", "12th grade"));
        }*/

//        Deal deal = dealList.get(0);
//        deal.setExpectedSLA(expectedSLAList.get(0));
//        deal.setQueueGroup(queueGroupList.get(0));
        Deal.persist(dealList);
    }

    public enum DemoData {
        NONE,
        SMALL,
        LARGE
    }

}
