package pl.ug.edu.planner.userSubject.model;

import jakarta.persistence.*;
import lombok.*;
import pl.ug.edu.planner.subject.model.Subject;
import pl.ug.edu.planner.user.model.User;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_subject")
public class UserSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private DayOfWeek dayOfWeek;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;

    public void setTime(LocalDateTime time) {
        setTimeStart(time);
        setTimeEnd(time.plusHours(1).plusMinutes(30));
    }
}