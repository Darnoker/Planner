package pl.ug.edu.planner.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ug.edu.planner.jwt.service.JwtService;

@RestController
@RequestMapping("/api/demo")
@RequiredArgsConstructor
public class DemoController {

    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<String> sayHello(@RequestHeader(name = "Authorization") String header) {
        boolean valid = jwtService.isTokenValid(header);
        if(jwtService.isTokenValid(header)) {
            return ResponseEntity.ok("hello friend");
        } else {
            return ResponseEntity.status(403).build();
        }
    }
}
