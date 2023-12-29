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
        if (subjectOptional.isPresent()) {
            User user = userOptional.get();
            Subject subject = subjectOptional.get();
            user.getSubjects().add(subject);
            subject.getUsers().add(user);
            userService.save(user);
            subjectService.save(subject);
            Optional<UserSubject> userSubjectOptional = getUserSubject(new UserSubjectGetRequest(user.getId(), subject.getId()));
            UserSubject userSubject;

            if (userSubjectOptional.isPresent()) {
                userSubject = userSubjectOptional.get();
                userSubject.setDayOfWeek(request.getDayOfWeek());
                userSubject.setTimeStart(request.getTimeStart());
                userSubject.setTimeEnd(request.getTimeEnd());
            } else {
                userSubject = UserSubject
                        .builder()
                        .user(user)
                        .subject(subject)
                        .dayOfWeek(request.getDayOfWeek())
                        .timeStart(request.getTimeStart())
                        .timeEnd(request.getTimeEnd())
                        .build();
            }

            return userSubjectRepository.save(userSubject);

        } else {
            User user = userOptional.get();
            Subject subject = Subject.builder()
                    .name(request.getSubjectName())
                    .users(new HashSet<>())
                    .build();

            subject.getUsers().add(user);
            user.getSubjects().add(subject);
            subjectService.save(subject);
            userService.save(user);

            UserSubject userSubject = UserSubject.builder()
                    .user(user)
                    .subject(subject)
                    .dayOfWeek(request.getDayOfWeek())
                    .timeStart(request.getTimeStart())
                    .timeEnd(request.getTimeEnd())
                    .build();

            return userSubjectRepository.save(userSubject);
        }
    }
}
