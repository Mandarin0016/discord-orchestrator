package orchestrator.user.repository;

import java.util.Optional;
import java.util.UUID;
import orchestrator.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUsernameOrEmail(String username, String email);

    Optional<User> findByUsernameOrEmail(String username, String email);
}
