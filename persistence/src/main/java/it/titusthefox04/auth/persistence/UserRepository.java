package it.titusthefox04.auth.persistence;

import it.titusthefox04.auth.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository of {@link User} entities.
 *
 * @author titusthefox04
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> getByUsername(String username);

    long countByUsername(String username);
}
