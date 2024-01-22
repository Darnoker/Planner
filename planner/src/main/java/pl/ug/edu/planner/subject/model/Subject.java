package pl.ug.edu.planner.subject.model;

import jakarta.persistence.*;
import lombok.*;
import pl.ug.edu.planner.user.model.User;

import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @ToString.Exclude
    @ManyToMany(mappedBy = "subjects", fetch = FetchType.LAZY)
    private Set<User> users;


    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Subject subject = (Subject) obj;
        return Objects.equals(id, subject.id) ||
                Objects.equals(name, subject.name);

    }
}
