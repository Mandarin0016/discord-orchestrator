package orchestrator.user.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import orchestrator.user.command.input.UserLoginInput;
import orchestrator.user.command.input.UserRegisterInput;
import orchestrator.user.command.output.UserProfileOutput;
import orchestrator.user.exception.UserDomainException;
import orchestrator.user.model.User;
import orchestrator.user.model.mapper.EntityMapper;
import orchestrator.user.properties.UserProperties;
import orchestrator.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserProperties userProperties;

    public UserService(UserRepository userRepository, UserProperties userProperties) {

        this.userRepository = userRepository;
        this.userProperties = userProperties;
    }

    public UserProfileOutput register(UserRegisterInput registerInputCommand) {

        User userToBeSave = EntityMapper.mapToUser(registerInputCommand);
        this.validate(userToBeSave);
        this.initialize(userToBeSave);
        User savedUsed = userRepository.save(userToBeSave);

        log.info("User with id=[%s], email=[%s] successfully registered".formatted(savedUsed.getId(), savedUsed.getEmail()));
        return EntityMapper.mapToProfileOutput(savedUsed);
    }

    public UserProfileOutput login(UserLoginInput loginInputCommand) {

        User user = userRepository.findByUsernameOrEmail(loginInputCommand.getUsernameOrEmail(),
                        loginInputCommand.getUsernameOrEmail())
                .orElseThrow(() -> new UserDomainException("User with given email or username does not exist."));

        if (!user.isActive()) {
            log.warn("Deactivated user with id=[%s], email=[%s] attempted login.".formatted(user.getId(), user.getEmail()));
            throw new UserDomainException("User with id=[%s] is deactivated.".formatted(user.getId()));
        }

        String userPassword = user.getPassword();
        String loginPassword = getHashBase64(loginInputCommand.getPassword());

        if (!userPassword.equals(loginPassword)) {
            throw new UserDomainException("Invalid given credentials for user with id=[%s].".formatted(user.getId()));
        }

        log.info("User with id=[%s], email=[%s] successfully logged in.".formatted(user.getId(), user.getEmail()));
        return EntityMapper.mapToProfileOutput(user);
    }

    private void validate(User user) {

        boolean exist = userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail());

        if (exist) {
            throw new UserDomainException("User with given email or username already exists.");
        }
    }

    private void initialize(User user) {

        final String hashedPassword = getHashBase64(user.getPassword());
        user.setPassword(hashedPassword);
        user.setActive(userProperties.getDefaultAccountState());
        user.setRole(userProperties.getDefaultRole());
        user.setAuthorities(userProperties.getDefaultAuthorities());
    }

    private static String getHashBase64(String plainText) {

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new UserDomainException("User to initialize user at the moment.");
        }

        byte[] raw = md.digest(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(md.digest(raw));
    }
}
