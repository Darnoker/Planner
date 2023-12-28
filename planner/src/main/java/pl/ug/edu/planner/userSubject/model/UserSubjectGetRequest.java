package pl.ug.edu.planner.userSubject.model;

import lombok.Data;

@Data
public class UserSubjectGetRequest {
    private Long userId;
    private Long subjectId;
}
