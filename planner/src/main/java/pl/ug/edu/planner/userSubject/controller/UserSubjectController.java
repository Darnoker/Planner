package pl.ug.edu.planner.userSubject.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ug.edu.planner.userSubject.model.UserSubject;
import pl.ug.edu.planner.userSubject.model.UserSubjectGetRequest;
import pl.ug.edu.planner.userSubject.service.UserSubjectService;

@RestController
@RequestMapping("/user-subject")
@AllArgsConstructor
public class UserSubjectController {

    private final UserSubjectService userSubjectService;

    @GetMapping("/get")
    public ResponseEntity<UserSubject> getUserSubject(@RequestBody UserSubjectGetRequest request) {
        return userSubjectService
                .getUserSubject(request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
