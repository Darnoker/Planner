package pl.ug.edu.planner.user.service;

import org.springframework.stereotype.Service;
import pl.ug.edu.planner.service.BaseService;
import pl.ug.edu.planner.user.model.User;
import pl.ug.edu.planner.user.repository.UserRepository;

@Service
public class UserService extends BaseService<User, Long> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }


}
