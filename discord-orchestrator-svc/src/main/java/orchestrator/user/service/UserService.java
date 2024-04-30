package orchestrator.user.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import orchestrator.user.command.input.UserLoginInput;
import orchestrator.user.command.input.UserRegisterInput;
import orchestrator.user.command.output.UserProfileOutput;
import orchestrator.user.exception.UserDomainException;
import orchestrator.user.model.User;
import orchestrator.user.model.SystemAuthority;
import orchestrator.user.model.UserRole;
import orchestrator.user.model.mapper.EntityMapper;
import orchestrator.user.property.UserProperties;
import orchestrator.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserProperties userProperties;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserProperties userProperties, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.userProperties = userProperties;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public User getById(UUID userId, boolean lockRequired) {

        Optional<User> user;

        if (lockRequired) {
            user = userRepository.findByIdAndLock(userId);
        } else {
            user = userRepository.findById(userId);
        }

        if (user.isEmpty()) {
            throw new UserDomainException("User with id=[%s] does not exists.".formatted(userId));
        }

        return user.get();
    }

    public void updateUserDiscordId(UUID userId, String discordId) {

        User user = getById(userId, true);

        if (user.getDiscordId() != null) {
            log.info("Updating `user_discord_id` for User with id=[%s]. Transition [%s] -> [%s].".formatted(user.getId(), user.getDiscordId(), discordId));
        }

        user.setDiscordId(discordId);
        userRepository.save(user);
    }

    public void updateUserDiscordAuthorizationStatus(UUID userId, boolean isAuthorized) {

        User user = getById(userId, true);
        user.setDiscordAuthorized(isAuthorized);
        userRepository.save(user);
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

        User user = userRepository.findByUsernameOrEmail(loginInputCommand.getUsernameOrEmail(), loginInputCommand.getUsernameOrEmail()).orElseThrow(() -> new UserDomainException("Incorrect username or password."));

        String userPassword = user.getPassword();
        String loginPassword = passwordEncoder.getHashBase64(loginInputCommand.getPassword());

        if (!userPassword.equals(loginPassword)) {
            throw new UserDomainException("Incorrect username or password.");
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

        final String hashedPassword = passwordEncoder.getHashBase64(user.getPassword());
        boolean isActive = userProperties.getDefaultAccountState();
        UserRole defaultRole = userProperties.getDefaultRole();
        Set<SystemAuthority> defaultAuthorities = userProperties.getDefaultAuthorities();

        user.setPassword(hashedPassword);
        user.setActive(isActive);
        user.setRole(defaultRole);
        user.setAuthorities(defaultAuthorities);
        user.setDiscordAuthorized(false);
    }
}
