package pl.ug.edu.planner.userSubject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSubjectGetRequest {
    private Long userId;
    private Long subjectId;
}
