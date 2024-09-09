package org.medx.elixrlabs.repository;

import org.medx.elixrlabs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing User data from the database.
 *
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for User entities.
 * Custom queries can be defined by adding method signatures that follow the naming
 * convention understood by Spring Data JPA.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    String getUserWithRole = "FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email AND u.isBlocked = false";

    @Query(getUserWithRole)
    User findByEmailWithRole(@Param("email") String email);

}
