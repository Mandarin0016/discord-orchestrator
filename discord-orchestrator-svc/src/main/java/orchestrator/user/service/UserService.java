package orchestrator.user.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import orchestrator.user.command.UserRegisterInput;
import orchestrator.user.command.UserRegisterOutput;
import orchestrator.user.exception.UserDomainException;
import orchestrator.user.model.User;
import orchestrator.user.model.mapper.EntityMapper;
import orchestrator.user.properties.UserProperties;
import orchestrator.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProperties userProperties;

    public UserService(UserRepository userRepository, UserProperties userProperties) {

        this.userRepository = userRepository;
        this.userProperties = userProperties;
    }

    public UserRegisterOutput register(UserRegisterInput registerInputCommand) {

        User userToBeSave = EntityMapper.mapToUser(registerInputCommand);
        this.validate(userToBeSave);
        this.initialize(userToBeSave);
        User savedUsed = userRepository.save(userToBeSave);

        return EntityMapper.mapToRegisterOutput(savedUsed);
    }

    private void validate(User user) {

        boolean exist = userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail());

        if (exist){
            throw new UserDomainException("User with given email or username already exists!");
        }
    }

    private void initialize(User user)  {

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new UserDomainException("User to initialize user at the moment.");
        }
        byte[] raw = md.digest(user.getPassword().getBytes(StandardCharsets.UTF_8));
        final String hashBase64 = Base64.getEncoder().encodeToString(md.digest(raw));

        user.setPassword(hashBase64);
        user.setActive(userProperties.getDefaultAccountState());
        user.setRole(userProperties.getDefaultRole());
        user.setAuthorities(userProperties.getDefaultAuthorities());
    }
}
