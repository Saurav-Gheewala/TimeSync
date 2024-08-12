package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
public class  TimetableService {

    private final Random random = new Random();
    @Autowired
    SampleDataService sampleDataService;

    public List<String> createTimetable() {
        List<String> timetable = new ArrayList<>();
        timetable.add(" Slot " +  "-" + "            " +"Subject With Teacher " + "-" + "        Timing");
        // Assuming each day starts at 9:00 AM and ends at 5:00 PM
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        LocalTime currentTime = startTime;

        // Keep track of assigned courses for each day
        Set<String> assignedCourses = new HashSet<>();

        for (int day = 0; day < 6; day++)
        { // Loop for 6 days of the week
            timetable.add(" "  +  "-" + "                       Day:"+ (day + 1) +"   " + "-" + " ");
            timetable.add("Day " + (day + 1) + ":");

            // Reset assigned courses for the new day
            assignedCourses.clear();

            for (int lecture = 0; lecture < 6; lecture++) { // 5 lectures per day
                if (currentTime.isBefore(endTime) && !isBetween(currentTime, LocalTime.of(13, 0), LocalTime.of(14, 0))) {
                    String course;
                    do {
                        course = sampleDataService.getCourses().get(random.nextInt(sampleDataService.getCourses().size()));
                    } while (assignedCourses.contains(course)); // Ensure course is not already assigned
                    assignedCourses.add(course);

                    String teacher = sampleDataService.getTeachers().get(random.nextInt(sampleDataService.getTeachers().size()));
                    timetable.add("Slot " + (lecture + 1) + "-" + "              " + course + " with " + teacher + " - " + currentTime + " : " + currentTime.plusHours(1));
                    currentTime = currentTime.plusHours(1);
                } else {
                    timetable.add("Slot " + (lecture + 1) + "-" + "              " + "Break" + " - " + currentTime + " : " + currentTime.plusHours(1));
                    currentTime = currentTime.plusHours(1);
                }
            }

            if (currentTime.isBefore(endTime) && !isBetween(currentTime, LocalTime.of(13, 0), LocalTime.of(14, 0))) { // 1 laboratory per day
                String laboratory = sampleDataService.getLaboratories().get(day % sampleDataService.getLaboratories().size());
                String teacher = sampleDataService.getTeachers().get(random.nextInt(sampleDataService.getTeachers().size()));
                timetable.add("Laboratory " + "-" + "              " + laboratory + " with " + teacher + " - " + currentTime + " : " + currentTime.plusHours(2));
                currentTime = currentTime.plusHours(2);
            } else {
                currentTime = currentTime.plusHours(1);
            }

            // Reset currentTime for the next day
            currentTime = startTime;

        }

        return timetable;
    }

    private boolean isBetween(LocalTime time, LocalTime startTime, LocalTime endTime) {
        return !time.isBefore(startTime) && time.isBefore(endTime);
    }

}
