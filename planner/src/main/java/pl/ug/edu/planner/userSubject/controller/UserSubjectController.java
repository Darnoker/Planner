package pl.ug.edu.planner.userSubject.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.ug.edu.planner.userSubject.model.UserSubject;
import pl.ug.edu.planner.userSubject.model.UserSubjectAddRequest;
import pl.ug.edu.planner.userSubject.model.UserSubjectGetRequest;
import pl.ug.edu.planner.userSubject.service.UserSubjectService;

import java.net.URI;
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

    @PostMapping("/add")
    public ResponseEntity<UserSubject> addUserSubject(@RequestBody UserSubjectAddRequest request) throws Exception {
        UserSubject userSubject = userSubjectService.addUserSubject(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userSubject.getId())
                .toUri();

        return ResponseEntity.created(location).body(userSubject);

    }
}
