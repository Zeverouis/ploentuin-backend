package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsernameIgnoreCase(String username);
    Optional<User> findByEmailIgnoreCase(String email);

    List<User> findAllByEmailVerified(boolean verified);

    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByResetToken(String resetToken);

    Optional<User> findByEmailVerificationToken(String token);
}
