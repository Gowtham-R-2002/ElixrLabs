package org.medx.elixrlabs.repository;

import org.medx.elixrlabs.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    String getAdminByEmail = "FROM Admin a LEFT JOIN FETCH a.user u WHERE u.email = :email AND a.isDeleted = false";

    @Query(getAdminByEmail)
    Admin findByEmail(@Param("email") String email);
}
