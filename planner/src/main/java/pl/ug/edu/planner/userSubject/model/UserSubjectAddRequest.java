package pl.ug.edu.planner.userSubject.model;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class UserSubjectAddRequest {
    private Long userId;
    private String subjectName;
    private DayOfWeek dayOfWeek;
    private LocalTime timeStart;
    private LocalTime timeEnd;
}
