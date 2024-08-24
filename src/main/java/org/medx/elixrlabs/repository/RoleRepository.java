package org.medx.elixrlabs.repository;

import org.medx.elixrlabs.model.Role;
import org.medx.elixrlabs.util.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    String fetchRoleQuery = "From Role WHERE name = :name";

    Role findByName(@Param("name") RoleEnum name);
}
