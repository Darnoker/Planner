package pl.ug.edu.planner.classes;

import jakarta.persistence.*;
import lombok.*;
import pl.ug.edu.planner.user.model.User;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subject")
@EqualsAndHashCode
public class Subject {

    @Id
    private Long id;
    private String name;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;

    @ToString.Exclude
    @ManyToMany(mappedBy = "subjects", fetch = FetchType.LAZY)
    private Set<User> users;

    public void setTime(LocalDateTime time) {
        setTimeStart(time);
        setTimeEnd(time.plusHours(1).plusMinutes(30));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, timeStart, timeEnd);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Subject subject = (Subject) obj;
        return Objects.equals(id, subject.id) &&
                Objects.equals(name, subject.name) &&
                Objects.equals(timeStart, subject.timeStart) &&
                Objects.equals(timeEnd, subject.timeEnd);
    }
}
