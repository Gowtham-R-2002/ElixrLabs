package org.medx.elixrlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.medx.elixrlabs.model.Role;
import org.medx.elixrlabs.util.RoleEnum;

/**
 * Repository interface for accessing Role data from the database.
 *
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for Role entities.
 * Custom queries can be defined by adding method signatures that follow the naming
 * convention understood by Spring Data JPA.
 * </p>
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    String fetchRoleQuery = "From Role WHERE name = :name";

    Role findByName(@Param("name") RoleEnum name);
}
