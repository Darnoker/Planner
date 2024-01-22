package pl.ug.edu.planner.userSubject.model;

import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Builder
public class UserSubjectDTO {
    private String subjectName;
    private String teacher;
    private String room;
    private DayOfWeek dayOfWeek;
    private LocalTime timeStart;
    private LocalTime timeEnd;
}
