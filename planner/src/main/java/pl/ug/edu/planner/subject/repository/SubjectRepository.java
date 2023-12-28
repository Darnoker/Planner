package pl.ug.edu.planner.subject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ug.edu.planner.subject.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
