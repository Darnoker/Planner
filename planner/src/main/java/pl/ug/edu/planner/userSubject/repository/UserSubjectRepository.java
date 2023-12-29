package pl.ug.edu.planner.userSubject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.ug.edu.planner.userSubject.model.UserSubject;

@Repository
public interface UserSubjectRepository extends JpaRepository<UserSubject, Long> {
    @Query("SELECT us FROM UserSubject us WHERE us.user.id = :userId AND us.subject.id =:subjectId")
    UserSubject findUserSubjectByUserIdAndSubjectId(@Param("userId") Long userId, @Param("subjectId") Long subjectId);
}
