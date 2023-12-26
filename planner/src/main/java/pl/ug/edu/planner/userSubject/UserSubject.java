package pl.ug.edu.planner.userSubject;

import jakarta.persistence.*;
import lombok.*;
import pl.ug.edu.planner.classes.Subject;
import pl.ug.edu.planner.user.model.User;

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
}