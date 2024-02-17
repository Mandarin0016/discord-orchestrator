package orchestrator.user.repository;

import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.LockModeType;
import orchestrator.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUsernameOrEmail(String username, String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    @Query(value = """ 
            SELECT u FROM User u
            WHERE u.id = ?1
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<User> findByIdAndLock(UUID userId);
}
