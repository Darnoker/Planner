package pl.ug.edu.planner.subject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.ug.edu.planner.service.BaseService;
import pl.ug.edu.planner.subject.model.Subject;
import pl.ug.edu.planner.subject.repository.SubjectRepository;


@Service
@Slf4j
public class SubjectService extends BaseService<Subject, Long> {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        super(subjectRepository);
        this.subjectRepository = subjectRepository;
    }
}
