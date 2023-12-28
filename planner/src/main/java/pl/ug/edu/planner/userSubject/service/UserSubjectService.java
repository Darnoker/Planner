package pl.ug.edu.planner.userSubject.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ug.edu.planner.userSubject.model.UserSubject;
import pl.ug.edu.planner.userSubject.model.UserSubjectGetRequest;
import pl.ug.edu.planner.userSubject.repository.UserSubjectRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSubjectService {
    private UserSubjectRepository userSubjectRepository;

    public Optional<UserSubject> getUserSubject(UserSubjectGetRequest request) {
        return Optional.of(userSubjectRepository.findUserSubjectByUserIdAndSubjectId(request.getUserId(), request.getSubjectId()));
    }
}
