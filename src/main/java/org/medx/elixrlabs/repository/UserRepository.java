package org.medx.elixrlabs.repository;

import org.medx.elixrlabs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    String getUserWithRoles = "FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email";

    @Query(getUserWithRoles)
    User findByEmailAndIsDeletedFalse(@Param("email") String email);

}
