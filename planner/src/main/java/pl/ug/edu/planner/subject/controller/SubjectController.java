package pl.ug.edu.planner.subject.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.ug.edu.planner.subject.model.Subject;
import pl.ug.edu.planner.subject.service.SubjectService;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/subject")
@AllArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("/add")
    public ResponseEntity<Subject> addSubject(@RequestBody Subject subject) {
        Subject subjectAdded = subjectService.save(subject);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(subjectAdded.getId())
                .toUri();

        return ResponseEntity.created(location).body(subjectAdded);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getById(@PathVariable Long id) {
        return subjectService
                .getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Subject>> getAll() {
        return ResponseEntity.ok(subjectService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> put(@PathVariable Long id, @RequestBody Subject subject) {
        return subjectService.putById(id, subject).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Optional<Void> result = subjectService.deleteById(id);
        if (result.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
