package pl.ug.edu.planner.userSubject.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ug.edu.planner.subject.model.Subject;
import pl.ug.edu.planner.subject.service.SubjectService;
import pl.ug.edu.planner.user.model.User;
import pl.ug.edu.planner.user.service.UserService;
import pl.ug.edu.planner.userSubject.model.UserSubject;
import pl.ug.edu.planner.userSubject.model.UserSubjectAddRequest;
import pl.ug.edu.planner.userSubject.model.UserSubjectGetRequest;
import pl.ug.edu.planner.userSubject.repository.UserSubjectRepository;

import java.util.HashSet;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSubjectService {
    private UserSubjectRepository userSubjectRepository;
    private SubjectService subjectService;
    private UserService userService;

    public Optional<UserSubject> getUserSubject(UserSubjectGetRequest request) {
        return Optional.of(userSubjectRepository.findUserSubjectByUserIdAndSubjectId(request.getUserId(), request.getSubjectId()));
    }

    public UserSubject addUserSubject(UserSubjectAddRequest request) throws Exception {
        Optional<User> userOptional = userService.getById(request.getUserId());
        Optional<Subject> subjectOptional = subjectService.getByName(request.getSubjectName());
        if (userOptional.isEmpty()) {
            throw new Exception("User not found");
        }
        User user = userOptional.get();
        Subject subject;
        if (subjectOptional.isPresent()) {
            subject = subjectOptional.get();
            user.getSubjects().add(subject);
            subject.getUsers().add(user);

        } else {
            subject = Subject.builder()
                    .name(request.getSubjectName())
                    .users(new HashSet<>())
                    .build();

            subject.getUsers().add(user);
            user.getSubjects().add(subject);
        }
        return saveUserSubject(request, user, subject);
    }

    private UserSubject saveUserSubject(UserSubjectAddRequest request, User user, Subject subject) throws Exception {
        subjectService.save(subject);
        userService.save(user);
        Optional<UserSubject> userSubjectOptional = getUserSubject(new UserSubjectGetRequest(user.getId(), subject.getId()));
        UserSubject userSubject;
        if (userSubjectOptional.isEmpty()) {
            throw new Exception("User subject doesn't exist");

        } else {
            userSubject = userSubjectOptional.get();
            userSubject.setDayOfWeek(request.getDayOfWeek());
            userSubject.setTimeStart(request.getTimeStart());
            userSubject.setTimeEnd(request.getTimeEnd());
        }

        return userSubjectRepository.save(userSubject);
    }
}
