package pl.ug.edu.planner.userSubject.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.ug.edu.planner.jwt.service.JwtService;
import pl.ug.edu.planner.user.service.UserService;
import pl.ug.edu.planner.userSubject.model.UserSubject;
import pl.ug.edu.planner.userSubject.model.UserSubjectDTO;
import pl.ug.edu.planner.userSubject.model.UserSubjectGetRequest;
import pl.ug.edu.planner.userSubject.service.UserSubjectService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user-subject")
@AllArgsConstructor
@CrossOrigin
public class UserSubjectController {
    private final UserSubjectService userSubjectService;
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/get")
    public ResponseEntity<UserSubject> getUserSubject(@RequestBody UserSubjectGetRequest request) {
        return userSubjectService
                .getUserSubject(request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<UserSubject> addUserSubject(@RequestHeader(name = "Authorization") String header, @RequestBody UserSubjectDTO request) throws Exception {
        UserSubject userSubject = userSubjectService.addUserSubject(header, request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userSubject.getId())
                .toUri();

        return ResponseEntity.created(location).body(userSubject);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserSubjectDTO>> getUserSubjects(@RequestHeader(name = "Authorization") String header) {
        return ResponseEntity.ok(this.userSubjectService.getAll(header));
    }
}
