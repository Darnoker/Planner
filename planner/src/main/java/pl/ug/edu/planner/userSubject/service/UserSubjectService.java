package pl.ug.edu.planner.userSubject.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ug.edu.planner.jwt.service.JwtService;
import pl.ug.edu.planner.subject.model.Subject;
import pl.ug.edu.planner.subject.service.SubjectService;
import pl.ug.edu.planner.user.model.User;
import pl.ug.edu.planner.user.service.UserService;
import pl.ug.edu.planner.userSubject.model.UserSubject;
import pl.ug.edu.planner.userSubject.model.UserSubjectDTO;
import pl.ug.edu.planner.userSubject.model.UserSubjectGetRequest;
import pl.ug.edu.planner.userSubject.repository.UserSubjectRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSubjectService {
    private UserSubjectRepository userSubjectRepository;
    private SubjectService subjectService;
    private UserService userService;
    private JwtService jwtService;

    public Optional<UserSubject> getUserSubject(UserSubjectGetRequest request) {
        return Optional.of(userSubjectRepository.findUserSubjectByUserIdAndSubjectId(request.getUserId(), request.getSubjectId()));
    }

    public UserSubject addUserSubject(String header, UserSubjectDTO request) throws Exception {
        if(header.startsWith("Bearer ")) {
            header = header.substring("Bearer ".length() - 1);
        }

        String username = jwtService.extractUsername(header);
        Optional<User> userOptional = userService.getByEmail(username);
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

    private UserSubject saveUserSubject(UserSubjectDTO request, User user, Subject subject) throws Exception {
        subjectService.save(subject);
        userService.save(user);
        Optional<UserSubject> userSubjectOptional = getUserSubject(new UserSubjectGetRequest(user.getId(), subject.getId()));
        UserSubject userSubject;
        if (userSubjectOptional.isEmpty()) {
            throw new Exception("User subject doesn't exist");

        } else {
            userSubject = userSubjectOptional.get();
            userSubject.setTeacher(request.getTeacher());
            userSubject.setRoom(request.getRoom());
            userSubject.setDayOfWeek(request.getDayOfWeek());
            userSubject.setTimeStart(request.getTimeStart());
            userSubject.setTimeEnd(request.getTimeEnd());
        }

        return userSubjectRepository.save(userSubject);
    }

    public List<UserSubjectDTO> getAll(String header) {
        if(header.startsWith("Bearer ")) {
            header = header.substring("Bearer ".length() - 1);
        }

        String username = jwtService.extractUsername(header);
        Optional<User> userOptional = userService.getByEmail(username);
        List<UserSubjectDTO> userSubjectDTOList = new ArrayList<>();
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            var userSubjects = userSubjectRepository.findAllByUserId(user.getId());

            userSubjects.forEach((userSubject -> {
                UserSubjectDTO userSubjectDTO  = UserSubjectDTO
                        .builder()
                        .subjectName(this.subjectService.getById(userSubject.getSubject().getId()).get().getName())
                        .teacher(userSubject.getTeacher())
                        .room(userSubject.getRoom())
                        .dayOfWeek(userSubject.getDayOfWeek())
                        .timeStart(userSubject.getTimeStart())
                        .timeEnd(userSubject.getTimeEnd())
                        .build();

                userSubjectDTOList.add(userSubjectDTO);
            }));
        }
        return userSubjectDTOList;
    }
}
